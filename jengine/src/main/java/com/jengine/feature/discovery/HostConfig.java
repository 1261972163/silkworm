package com.jengine.feature.discovery;

/**
 * @author nouuid
 * @date 4/18/2016
 * @description
 */
public class HostConfig {
    private long hostId;
    private String hostName;
    private String ip;

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
