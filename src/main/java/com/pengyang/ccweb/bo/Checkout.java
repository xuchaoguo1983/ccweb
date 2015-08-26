package com.pengyang.ccweb.bo;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.pengyang.ccweb.Constants;

public class Checkout implements Serializable {
	private static final long serialVersionUID = 2223468342815529896L;

	private Integer checkoutId;
	private String outPlace;
	private String outTime;
	private Integer userId;
	private String userName;
	private String userPhoto;
	private String content;
	private String pictures;
	private Integer clientId;
	private String clientName;
	private String informUserIds;
	private String informUserNames;

	public Integer getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(Integer checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getOutPlace() {
		return outPlace;
	}

	public void setOutPlace(String outPlace) {
		this.outPlace = outPlace;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
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

	public String getUserPhoto() {
		if (StringUtils.isEmpty(userPhoto))
			return Constants.DEFAULT_USER_PHOTO;
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getInformUserIds() {
		return informUserIds;
	}

	public void setInformUserIds(String informUserIds) {
		this.informUserIds = informUserIds;
	}

	public String getInformUserNames() {
		return informUserNames;
	}

	public void setInformUserNames(String informUserNames) {
		this.informUserNames = informUserNames;
	}

}
