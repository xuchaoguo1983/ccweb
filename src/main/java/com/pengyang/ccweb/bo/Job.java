package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class Job implements Serializable {
	private static final long serialVersionUID = 3997476104661261276L;

	private String jobId;
	private String jobName;
	private int ifBase;
	private int jobLevel;
	private int ifViewBranch;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getIfBase() {
		return ifBase;
	}

	public void setIfBase(int ifBase) {
		this.ifBase = ifBase;
	}

	public int getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}

	public int getIfViewBranch() {
		return ifViewBranch;
	}

	public void setIfViewBranch(int ifViewBranch) {
		this.ifViewBranch = ifViewBranch;
	}

}
