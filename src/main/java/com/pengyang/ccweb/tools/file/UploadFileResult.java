package com.pengyang.ccweb.tools.file;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UploadFileResult implements Serializable {

	private int error_no = 0;
	private String error_info = "上传文件失败";
	private String url = "";

	public int getError_no() {
		return error_no;
	}

	public void setError_no(int error_no) {
		this.error_no = error_no;
	}

	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("error_no:" + error_no);
		sb.append("\n");
		sb.append("error_info:" + error_info);
		sb.append("\n");
		sb.append("url:" + url);
		sb.append("\n");
		return sb.toString();
	}
}
