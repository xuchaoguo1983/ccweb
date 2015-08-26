package com.pengyang.ccweb.bo;

import java.io.Serializable;

import cjscpool2.ARResponse;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code = 0;
	private String message;
	private Object data;

	public static Message fromResponse(ARResponse resp) {
		Message m = new Message();
		m.setCode(resp.getErroNo());
		m.setMessage(resp.getErrorMsg());
		return m;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
