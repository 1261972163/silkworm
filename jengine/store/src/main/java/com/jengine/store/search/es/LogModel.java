package com.jengine.store.search.es;

import java.util.Date;

/**
 * @author bl07637
 * @date 5/26/2016
 * @description
 */
public class LogModel {
	private String id;
	private String interfaceName;
	private String methodName;
	private String appId;
	private String remoteHost;
    private Date date;
	private long sdate;
    private String localHost;
    private String localAppId;
    private boolean flag;
    private int type;
    private String log;
    private String uuid;
	private String globalID;
	private String keyword;

	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getRemoteHost() {
		return remoteHost;
	}
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getLocalHost() {
		return localHost;
	}
	public void setLocalHost(String localHost) {
		this.localHost = localHost;
	}
	public String getLocalAppId() {
		return localAppId;
	}
	public void setLocalAppId(String localAppId) {
		this.localAppId = localAppId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getSdate() {
		return sdate;
	}
	public void setSdate(long sdate) {
		this.sdate = sdate;
	}
	public String getGlobalID() {
		return globalID;
	}
	public void setGlobalID(String globalID) {
		this.globalID = globalID;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {

		return "id=" + id +
				",uuid=" + uuid +
				",globalID=" + globalID +
				",keyword=" + keyword +
				",interfaceName=" + interfaceName +
				",methodName=" + methodName +
				",appId=" + appId +
				",remoteHost=" + remoteHost +
				",localHost=" + localHost +
				",localAppId=" + localAppId +
				",log=" + log +
				",flag=" + flag +
				",type=" + type +
				",date=" + date +
				",sdate=" + sdate ;
	}
	
}
