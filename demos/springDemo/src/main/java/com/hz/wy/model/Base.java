package com.hz.wy.model;

import java.io.Serializable;
import java.util.Date;

public class Base implements Serializable {
	private static final long serialVersionUID = 565390120247741777L;

	private Long id;
	private Integer version;
	private Date createTime;
	private Date lastUpdateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
