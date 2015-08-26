package com.pengyang.ccweb.bo;

public class LoginQRCode {
	private int authType;// ��֤���� 1������Աƽ̨ 2����ͨ�û�ƽ̨
	private String code;
	private String client;
	private long createTime;

	public int getAuthType() {
		return authType;
	}

	public void setAuthType(int authType) {
		this.authType = authType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public boolean isActive() {
		long l = System.currentTimeMillis();
		// active in 5min
		return l - this.createTime < 1000L * 60 * 5;
	}

	@Override
	public String toString() {
		return authType + "," + code + "," + client + "," + createTime;
	}

}
