package com.jengine.common.system;

/**
 * @author nouuid
 * @date 8/5/2016
 * @description
 */
public class SystemInfoMonitorTest {

    @org.junit.Test
    public void test() throws Exception {
        SystemInfoMonitor service = new SystemInfoMonitor();
        SystemInfo monitorInfo = service.getSystemInfo();
        System.out.println("cpu占有率=" + monitorInfo.getCpuRatio());
        System.out.println("可使用内存=" + monitorInfo.getTotalRuntimeMemory());
        System.out.println("剩余内存=" + monitorInfo.getFreeRuntimeMemory());
        System.out.println("最大可使用内存=" + monitorInfo.getMaxRuntimeMemory());
        System.out.println("操作系统=" + monitorInfo.getOsName());
        System.out.println("总的物理内存=" + monitorInfo.getTotalPhysicalMemory() + "kb");
        System.out.println("剩余的物理内存=" + monitorInfo.getFreePhysicalMemory() + "kb");
        System.out.println("已使用的物理内存=" + monitorInfo.getUsedPhysicalMemory() + "kb");
        System.out.println("线程总数=" + monitorInfo.getThreadNum() + "kb");
    }
}
