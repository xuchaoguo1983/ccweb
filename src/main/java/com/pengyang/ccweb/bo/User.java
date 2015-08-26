package com.pengyang.ccweb.bo;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.pengyang.ccweb.Constants;

public class User implements Serializable {
	private static final long serialVersionUID = -6993824232284990836L;

	private String companyId;
	private String companyName;
	private Integer adminId;
	private String adminName;
	private String adminImage;
	private String adminMobile;
	private String jobName;

	private Member member;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		if (member != null)
			return member.getUserName();

		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminImage() {
		if (StringUtils.isEmpty(adminImage))
			return Constants.DEFAULT_USER_PHOTO;
		return adminImage;
	}

	public void setAdminImage(String adminImage) {
		this.adminImage = adminImage;
	}

	public String getAdminMobile() {
		if (member != null)
			return member.getMobile();
		return adminMobile;
	}

	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
