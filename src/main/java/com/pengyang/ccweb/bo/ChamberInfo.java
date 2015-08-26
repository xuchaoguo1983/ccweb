package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class ChamberInfo implements Serializable {
	private static final long serialVersionUID = 1874612943054176868L;

	private Integer infoId;
	private String title;
	private String content;
	private Integer publisherId;
	private String publisherName;
	private String publishTime;
	private Integer readTimes;
	private Integer isHot;// 1：热点 0：非热点
	private String hotImage;

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
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

	public Integer getReadTimes() {
		return readTimes;
	}

	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public String getHotImage() {
		return hotImage;
	}

	public void setHotImage(String hotImage) {
		this.hotImage = hotImage;
	}

}
