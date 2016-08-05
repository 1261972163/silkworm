package com.jengine.plugin.kafka;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nouuid
 * @date 5/26/2016
 * @description
 */
public class LogPackage {

	private List<MonitorLog> list = new ArrayList<MonitorLog>();

	public List<MonitorLog> getList() {
		return list;
	}

	public void setList(List<MonitorLog> list) {
		this.list = list;
	}

	public LogPackage() {
	}
	
	public LogPackage(List<MonitorLog> loglist) {
		this.list = loglist;
	}
	
}
