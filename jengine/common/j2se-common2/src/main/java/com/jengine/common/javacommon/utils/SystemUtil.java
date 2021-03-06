package  com.jengine.common.javacommon.utils;

import com.sun.management.OperatingSystemMXBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.util.StringTokenizer;

/**
 * Created by nouuid on 2015/4/28.
 */
public class SystemUtil {

  private static final int CPUTIME = 30;
  private static final int PERCENT = 100;
  private static final int FAULTLENGTH = 10;
  private static String linuxVersion = null;

  public static SystemInfo getSystemInfo() throws Exception {
    int kb = 1024;
//    int mb = kb * 1024;
    int unit = kb;

    // os
    String osName = System.getProperty("os.name");

    // cpu
    ThreadGroup parentThread;
    for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null;
         parentThread = parentThread.getParent()) {
      ;
    }
    int threadNum = parentThread.activeCount();
    double cpuRatio = 0;
    if (osName.toLowerCase().startsWith("windows")) {
      cpuRatio = getCpuRatioForWindows();
    } else {
      cpuRatio = getCpuRateForLinux();
    }

    // memory
    OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
        .getOperatingSystemMXBean();
    long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / unit;
    long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / unit;
    long usedMemory =
        (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / unit;

    // jvm memory
    long totalMemory = Runtime.getRuntime().totalMemory() / unit;
    long freeMemory = Runtime.getRuntime().freeMemory() / unit;
    long maxMemory = Runtime.getRuntime().maxMemory() / unit;

    SystemInfo systemInfo = new SystemInfo();
    systemInfo.setOsName(osName);
    systemInfo.setCpuRatio(cpuRatio);
    systemInfo.setThreadNum(threadNum);
    systemInfo.setTotalPhysicalMemory(totalMemorySize);
    systemInfo.setUsedPhysicalMemory(usedMemory);
    systemInfo.setFreePhysicalMemory(freePhysicalMemorySize);
    systemInfo.setTotalRuntimeMemory(totalMemory);
    systemInfo.setFreeRuntimeMemory(freeMemory);
    systemInfo.setMaxRuntimeMemory(maxMemory);
    return systemInfo;
  }

  private static double getCpuRateForLinux() {
    InputStream is = null;
    InputStreamReader isr = null;
    BufferedReader brStat = null;
    StringTokenizer tokenStat = null;
    try {
      System.out.println("Get usage rate of CUP , linux version: " + linuxVersion);
      Process process = Runtime.getRuntime().exec("top -b -n 1");
      is = process.getInputStream();
      isr = new InputStreamReader(is, "UTF-8");
      brStat = new BufferedReader(isr);
      if (linuxVersion.equals("2.4")) {
        brStat.readLine();
        brStat.readLine();
        brStat.readLine();
        brStat.readLine();
        tokenStat = new StringTokenizer(brStat.readLine());
        tokenStat.nextToken();
        tokenStat.nextToken();
        String user = tokenStat.nextToken();
        tokenStat.nextToken();
        String system = tokenStat.nextToken();
        tokenStat.nextToken();
        String nice = tokenStat.nextToken();
        System.out.println(user + " , " + system + " , " + nice);
        user = user.substring(0, user.indexOf("%"));
        system = system.substring(0, system.indexOf("%"));
        nice = nice.substring(0, nice.indexOf("%"));
        float userUsage = new Float(user).floatValue();
        float systemUsage = new Float(system).floatValue();
        float niceUsage = new Float(nice).floatValue();
        return (userUsage + systemUsage + niceUsage) / 100;
      } else {
        brStat.readLine();
        brStat.readLine();
        tokenStat = new StringTokenizer(brStat.readLine());
        tokenStat.nextToken();
        tokenStat.nextToken();
        tokenStat.nextToken();
        tokenStat.nextToken();
        tokenStat.nextToken();
        tokenStat.nextToken();
        tokenStat.nextToken();
        String cpuUsage = tokenStat.nextToken();
        System.out.println("CPU idle : " + cpuUsage);
        Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
        return (1 - usage.floatValue() / 100);
      }
    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
      freeResource(is, isr, brStat);
      return 1;
    } finally {
      freeResource(is, isr, brStat);
    }
  }

  private static void freeResource(InputStream is, InputStreamReader isr,
                                   BufferedReader br) {
    try {
      if (is != null) {
        is.close();
      }
      if (isr != null) {
        isr.close();
      }
      if (br != null) {
        br.close();
      }
    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }

  private static double getCpuRatioForWindows() {
    try {
      String procCmd = System.getenv("windir")
          + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
      long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
      Thread.sleep(CPUTIME);
      long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
      double res = 0.0;
      if (c0 != null && c1 != null) {
        long idletime = c1[0] - c0[0];
        long busytime = c1[1] - c0[1];
        res = (double) (PERCENT * (busytime)) / (busytime + idletime);
      }
      return res;
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0.0;
    }
  }

  private static long[] readCpu(final Process proc) {
    long[] retn = new long[2];
    LineNumberReader input = null;
    try {
      proc.getOutputStream().close();
      input = new LineNumberReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
      String line = input.readLine();
      if (line == null || line.length() < FAULTLENGTH) {
        return null;
      }
      int capidx = line.indexOf("Caption");
      int cmdidx = line.indexOf("CommandLine");
      int rocidx = line.indexOf("ReadOperationCount");
      int umtidx = line.indexOf("UserModeTime");
      int kmtidx = line.indexOf("KernelModeTime");
      int wocidx = line.indexOf("WriteOperationCount");
      long idletime = 0;
      long kneltime = 0;
      long usertime = 0;
      while ((line = input.readLine()) != null) {
        if (line.length() < wocidx) {
          continue;
        }
        String caption = StringUtils.substring(line, capidx, cmdidx - 1).trim();
        String cmd = StringUtils.substring(line, cmdidx, kmtidx - 1).trim();
        if (cmd.indexOf("wmic.exe") >= 0) {
          continue;
        }
        String s1 = StringUtils.substring(line, kmtidx, rocidx - 1).trim();
        String s2 = StringUtils.substring(line, umtidx, wocidx - 1).trim();
        if (caption.equals("System Idle Process") || caption.equals("System")) {
          if (s1.length() > 0) {
            idletime += Long.parseLong(s1);
          }
          if (s2.length() > 0) {
            idletime += Long.parseLong(s2);
          }
          continue;
        }
        if (s1.length() > 0) {
          kneltime += Long.parseLong(s1);
        }
        if (s2.length() > 0) {
          usertime += Long.parseLong(s2);
        }
      }
      retn[0] = idletime;
      retn[1] = kneltime + usertime;
      return retn;
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (input != null) {
          input.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static class SystemInfo {

    // os
    private String osName;

    // cpu
    private int threadNum;
    private double cpuRatio;

    // memory
    private long totalPhysicalMemory;
    private long usedPhysicalMemory;
    private long freePhysicalMemory;

    // jvm memory
    private long totalRuntimeMemory;
    private long freeRuntimeMemory;
    private long maxRuntimeMemory;


    public long getFreeRuntimeMemory() {
      return freeRuntimeMemory;
    }

    public void setFreeRuntimeMemory(long freeRuntimeMemory) {
      this.freeRuntimeMemory = freeRuntimeMemory;
    }

    public long getFreePhysicalMemory() {
      return freePhysicalMemory;
    }

    public void setFreePhysicalMemory(long freePhysicalMemory) {
      this.freePhysicalMemory = freePhysicalMemory;
    }

    public long getMaxRuntimeMemory() {
      return maxRuntimeMemory;
    }

    public void setMaxRuntimeMemory(long maxRuntimeMemory) {
      this.maxRuntimeMemory = maxRuntimeMemory;
    }

    public String getOsName() {
      return osName;
    }

    public void setOsName(String osName) {
      this.osName = osName;
    }

    public long getTotalRuntimeMemory() {
      return totalRuntimeMemory;
    }

    public void setTotalRuntimeMemory(long totalRuntimeMemory) {
      this.totalRuntimeMemory = totalRuntimeMemory;
    }

    public long getTotalPhysicalMemory() {
      return totalPhysicalMemory;
    }

    public void setTotalPhysicalMemory(long totalPhysicalMemory) {
      this.totalPhysicalMemory = totalPhysicalMemory;
    }

    public int getThreadNum() {
      return threadNum;
    }

    public void setThreadNum(int threadNum) {
      this.threadNum = threadNum;
    }

    public long getUsedPhysicalMemory() {
      return usedPhysicalMemory;
    }

    public void setUsedPhysicalMemory(long usedPhysicalMemory) {
      this.usedPhysicalMemory = usedPhysicalMemory;
    }

    public double getCpuRatio() {
      return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
      this.cpuRatio = cpuRatio;
    }
  }

}
