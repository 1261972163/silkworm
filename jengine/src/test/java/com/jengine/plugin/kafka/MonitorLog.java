package com.jengine.plugin.kafka;

/**
 * @author bl07637
 * @date 5/30/2016
 * @description
 */
public class MonitorLog {
    private String appId = "";
    private int type;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
