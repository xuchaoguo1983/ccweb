package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class TemplateDeptApproval implements Serializable {
	private static final long serialVersionUID = 2987070792354085596L;

	private Integer configId;
	private Integer userId;
	private String userName;

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
