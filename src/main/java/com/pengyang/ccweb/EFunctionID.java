package com.pengyang.ccweb;

public enum EFunctionID {
	LOGIN(640501), // 登陆
	QR_LOGIN(640502), // QR Code登陆

	DEPT_CREATE(640301), // 创建部门
	DEPT_UPDATE(640302), // 更新部门
	DEPT_LIST(640303), // 读取部门列表
	DEPT_DELETE(640304), // 删除部门
	DEPT_MOVE(640305), // 移动部门顺序
	DEPT_ALL(640313), // 查找所有部门

	JOB_LIST(640076), // 查询职务列表

	MEMBER_LIST(640306), // 查询部门成员
	MEMBER_CREATE(640308), // 添加成员
	MEMBER_UPDATE(640307), // 编辑成员
	MEMBER_VIEW(640312), // 查询成员信息
	MEMBER_DELETE(640309), // 删除成员
	MEMBER_MOVE(640310), // 成员部门移动
	MEMBER_UPLOAD(640311), // 成员批量上传
	MEMBER_AUDIT_LIST(640548), //查询成员修改记录

	TEMPLATE_CREATE(640081), // 新增模版
	TEMPLATE_LIST(640082), // 模版列表查询
	TEMPLATE_UPDATE(640083), // 编辑模版
	TEMPLATE_DELETE(640084), // 删除模版
	TEMPLATE_DEPT_LIST(640085), // 查询模版配置部门
	TEMPLATE_DEPT_ADD(640086), // 添加模版配置部门
	TEMPLATE_DEPT_DELETE(640087), // 删除模版配置部门
	TEMPLATE_APPROVAL_LIST(640088), // 查询模版配置审批人列表
	TEMPLATE_APPROVAL_ADD(640089), // 添加模版配置审批人
	TEMPLATE_APPROVAL_DELETE(640090), // 删除模版配置审批人

	ANNOUNCEMENT_CREATE(640530), // 添加公告
	ANNOUNCEMENT_LIST(640531), // 查询公告列表
	ANNOUNCEMENT_VERIFY(640533), // 审核公告
	ANNOUNCEMENT_VIEW(640534), // 查看公告详情
	ANNOUNCEMENT_UPDATE(640535), // 更新公告
	ANNOUNCEMENT_DELETE(640536), // 删除公告

	CHECKOUT_LIST(640520), // 外勤记录列表
	CHECKOUT_VIEW(640521), // 外勤记录详情

	CHAMBERINFO_LIST(640510), // 商会信息列表（新闻、通知、风采等）
	CHAMBERINFO_CREATE(640511), // 添加商会信息
	CHAMBERINFO_UPDATE(640512), // 更新商会信息
	CHAMBERINFO_DELETE(640513), // 删除商会信息
	CHAMBERINFO_VIEW(640514), // 查询商会详细信息
	;

	private int id;

	public int getId() {
		return this.id;
	}

	EFunctionID(int id) {
		this.id = id;
	}
}
