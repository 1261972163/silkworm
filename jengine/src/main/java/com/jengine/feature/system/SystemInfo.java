package com.jengine.feature.system;

/**
 * @author nouuid
 * @date 8/5/2016
 * @description
 */
public class SystemInfo {

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
