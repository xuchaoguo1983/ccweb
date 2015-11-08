package com.pengyang.ccweb;

public enum EFunctionID {
	LOGIN(640501), // ��½
	QR_LOGIN(640502), // QR Code��½

	DEPT_CREATE(640301), // ��������
	DEPT_UPDATE(640302), // ���²���
	DEPT_LIST(640303), // ��ȡ�����б�
	DEPT_DELETE(640304), // ɾ������
	DEPT_MOVE(640305), // �ƶ�����˳��
	DEPT_ALL(640313), // �������в���

	JOB_LIST(640076), // ��ѯְ���б�

	MEMBER_LIST(640306), // ��ѯ���ų�Ա
	MEMBER_CREATE(640308), // ��ӳ�Ա
	MEMBER_UPDATE(640307), // �༭��Ա
	MEMBER_VIEW(640312), // ��ѯ��Ա��Ϣ
	MEMBER_DELETE(640309), // ɾ����Ա
	MEMBER_MOVE(640310), // ��Ա�����ƶ�
	MEMBER_UPLOAD(640311), // ��Ա�����ϴ�
	MEMBER_AUDIT_LIST(640548), //��ѯ��Ա�޸ļ�¼

	TEMPLATE_CREATE(640081), // ����ģ��
	TEMPLATE_LIST(640082), // ģ���б��ѯ
	TEMPLATE_UPDATE(640083), // �༭ģ��
	TEMPLATE_DELETE(640084), // ɾ��ģ��
	TEMPLATE_DEPT_LIST(640085), // ��ѯģ�����ò���
	TEMPLATE_DEPT_ADD(640086), // ���ģ�����ò���
	TEMPLATE_DEPT_DELETE(640087), // ɾ��ģ�����ò���
	TEMPLATE_APPROVAL_LIST(640088), // ��ѯģ�������������б�
	TEMPLATE_APPROVAL_ADD(640089), // ���ģ������������
	TEMPLATE_APPROVAL_DELETE(640090), // ɾ��ģ������������

	ANNOUNCEMENT_CREATE(640530), // ��ӹ���
	ANNOUNCEMENT_LIST(640531), // ��ѯ�����б�
	ANNOUNCEMENT_VERIFY(640533), // ��˹���
	ANNOUNCEMENT_VIEW(640534), // �鿴��������
	ANNOUNCEMENT_UPDATE(640535), // ���¹���
	ANNOUNCEMENT_DELETE(640536), // ɾ������

	CHECKOUT_LIST(640520), // ���ڼ�¼�б�
	CHECKOUT_VIEW(640521), // ���ڼ�¼����

	CHAMBERINFO_LIST(640510), // �̻���Ϣ�б����š�֪ͨ����ɵȣ�
	CHAMBERINFO_CREATE(640511), // ����̻���Ϣ
	CHAMBERINFO_UPDATE(640512), // �����̻���Ϣ
	CHAMBERINFO_DELETE(640513), // ɾ���̻���Ϣ
	CHAMBERINFO_VIEW(640514), // ��ѯ�̻���ϸ��Ϣ
	;

	private int id;

	public int getId() {
		return this.id;
	}

	EFunctionID(int id) {
		this.id = id;
	}
}
