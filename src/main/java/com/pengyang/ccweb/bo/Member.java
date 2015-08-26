package com.pengyang.ccweb.bo;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.pengyang.ccweb.Constants;

public class Member implements Serializable {
	private static final long serialVersionUID = -1161097935377141406L;
	private Integer userId;
	private String userName;
	private String userImage;
	private String mobile;
	private String companyName;
	private String position;
	private String status;
	private String address;
	private String officeTell;
	private String birthPlace;

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

	public String getUserImage() {
		if (StringUtils.isEmpty(userImage))
			return Constants.DEFAULT_USER_PHOTO;
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOfficeTell() {
		return officeTell;
	}

	public void setOfficeTell(String officeTell) {
		this.officeTell = officeTell;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

}
