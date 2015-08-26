package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class Announcement implements Serializable {
	private static final long serialVersionUID = -6703996553122709710L;

	private Integer announcementId;
	private String title;
	private String content;
	private Integer publisherId;
	private String publisherName;
	private String publishTime;
	private Integer status;
	private Integer verifyUserId;
	private String verifyUserName;
	private String verifyDesc;
	private String verifyTime;
	private Integer target;
	private String targetIds;
	private String targetNames;

	public Integer getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(Integer announcementId) {
		this.announcementId = announcementId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVerifyUserId() {
		return verifyUserId;
	}

	public void setVerifyUserId(Integer verifyUserId) {
		this.verifyUserId = verifyUserId;
	}

	public String getVerifyUserName() {
		return verifyUserName;
	}

	public void setVerifyUserName(String verifyUserName) {
		this.verifyUserName = verifyUserName;
	}

	public String getVerifyDesc() {
		return verifyDesc;
	}

	public void setVerifyDesc(String verifyDesc) {
		this.verifyDesc = verifyDesc;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public String getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(String targetIds) {
		this.targetIds = targetIds;
	}

	public String getTargetNames() {
		return targetNames;
	}

	public void setTargetNames(String targetNames) {
		this.targetNames = targetNames;
	}

}
