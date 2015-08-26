package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class Dept implements Serializable {
	private static final long serialVersionUID = 205167253921773688L;
	private Integer deptId;
	private String deptName;
	private Integer index;// display order

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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
