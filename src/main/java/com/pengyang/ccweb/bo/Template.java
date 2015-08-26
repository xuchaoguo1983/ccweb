package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class Template implements Serializable {
	private static final long serialVersionUID = 4273941478997531101L;

	private Integer templateId;
	private String templateName;
	private Integer templateType; // 1费用 2出差 3请假 4其他
	private Integer createUserId; // 创建人

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

}
