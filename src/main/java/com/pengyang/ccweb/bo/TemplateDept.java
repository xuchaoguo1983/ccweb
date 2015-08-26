package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class TemplateDept implements Serializable {
	private static final long serialVersionUID = 3673374237308293817L;

	private Integer configId;
	private Integer templateId;
	private Integer deptId;
	private String deptName;

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
