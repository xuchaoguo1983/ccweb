package com.pengyang.ccweb.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cjscpool2.ARCorrespond;
import cjscpool2.ARRequest;
import cjscpool2.ARResponse;

import com.pengyang.ccweb.Constants;
import com.pengyang.ccweb.EFunctionID;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.Template;
import com.pengyang.ccweb.bo.TemplateDept;
import com.pengyang.ccweb.bo.TemplateDeptApproval;
import com.pengyang.ccweb.bo.User;

/**
 * 审批模版管理
 * 
 * @author xuchaoguo
 * 
 */
@Controller
public class TemplateController {
	private final Logger log = Logger.getLogger(TemplateController.class);

	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	public String index() {
		return "template";
	}

	@RequestMapping(value = "/templates", method = RequestMethod.POST)
	@ResponseBody
	public Message listTemplates(HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.TEMPLATE_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_status", 1);// 模板状态 1可用 0不可用 -1全部

		ARResponse resp = ARCorrespond.post(req);

		List<Template> list = new ArrayList<Template>();

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			while (resp.next()) {
				Template template = new Template();

				template.setTemplateId(Integer.valueOf(resp
						.getValue("template_id")));
				template.setTemplateName(resp.getValue("template_name"));
				template.setTemplateType(Integer.valueOf(resp
						.getValue("template_type")));

				list.add(template);
			}

			Collections.sort(list, new Comparator<Template>() {

				@Override
				public int compare(Template o1, Template o2) {
					if (o1.getTemplateType() > o2.getTemplateType())
						return 1;
					else if (o1.getTemplateType() < o2.getTemplateType())
						return -1;
					else
						return o1.getTemplateId() > o2.getTemplateId() ? 1 : -1;
				}

			});

			message.setData(list);
		}
		return message;
	}

	@RequestMapping(value = "/template", method = RequestMethod.POST)
	@ResponseBody
	public Message saveTemplate(@ModelAttribute("template") Template template,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);

		if (template.getTemplateId() != null) {
			// update
			req.setFunctionNo(EFunctionID.TEMPLATE_UPDATE.getId());
			req.setParam("i_template_id", template.getTemplateId());

		} else {
			// create
			req.setFunctionNo(EFunctionID.TEMPLATE_CREATE.getId());
			req.setParam("i_user_id", user.getAdminId());
		}

		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_template_name", template.getTemplateName());
		req.setParam("i_template_type", template.getTemplateType());

		ARResponse resp = ARCorrespond.post(req);
		if (resp.getErroNo() != 0)
			return Message.fromResponse(resp);
		return this.listTemplates(session);
	}

	@RequestMapping(value = "/template/{templateId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message removeTemplate(@PathVariable int templateId,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.TEMPLATE_DELETE.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_template_id", templateId);

		ARResponse resp = ARCorrespond.post(req);

		return Message.fromResponse(resp);
	}

	@RequestMapping(value = "/template/depts/{templateId}", method = RequestMethod.GET)
	@ResponseBody
	public Message listTemplateDepts(@PathVariable int templateId,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.TEMPLATE_DEPT_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_template_id", templateId);

		ARResponse resp = ARCorrespond.post(req);
		Message message = Message.fromResponse(resp);
		if (resp.getErroNo() == 0) {
			List<TemplateDept> list = new ArrayList<TemplateDept>();
			while (resp.next()) {
				TemplateDept templateDept = new TemplateDept();

				templateDept.setConfigId(Integer.valueOf(resp
						.getValue("config_id")));
				templateDept.setTemplateId(Integer.valueOf(resp
						.getValue("template_id")));
				templateDept
						.setDeptId(Integer.valueOf(resp.getValue("dept_id")));
				templateDept.setDeptName(resp.getValue("dept_name"));

				list.add(templateDept);
			}

			message.setData(list);
		}

		return message;
	}

	@RequestMapping(value = "/template/dept", method = RequestMethod.POST)
	@ResponseBody
	public Message addTemplateDept(
			@RequestParam(value = "templateId", required = true) int templateId,
			@RequestParam(value = "deptId", required = true) int deptId,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.TEMPLATE_DEPT_ADD.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_template_id", templateId);
		req.setParam("i_dept_id", deptId);

		ARResponse resp = ARCorrespond.post(req);
		if (resp.getErroNo() != 0)
			return Message.fromResponse(resp);
		return this.listTemplateDepts(templateId, session);
	}

	@RequestMapping(value = "/template/dept/{configId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message removeTemplateDept(@PathVariable int configId,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.TEMPLATE_DEPT_DELETE.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_config_id", configId);

		ARResponse resp = ARCorrespond.post(req);

		return Message.fromResponse(resp);
	}

	@RequestMapping(value = "/template/dept/approvals/{configId}", method = RequestMethod.GET)
	@ResponseBody
	public Message listTemplateDeptApprovals(@PathVariable int configId,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.TEMPLATE_APPROVAL_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_config_id", configId);

		ARResponse resp = ARCorrespond.post(req);
		Message message = Message.fromResponse(resp);
		if (resp.getErroNo() == 0) {
			List<TemplateDeptApproval> list = new ArrayList<TemplateDeptApproval>();
			while (resp.next()) {
				TemplateDeptApproval templateDeptApproval = new TemplateDeptApproval();

				templateDeptApproval.setConfigId(Integer.valueOf(resp
						.getValue("config_id")));
				templateDeptApproval.setUserId(Integer.valueOf(resp
						.getValue("approval")));
				templateDeptApproval.setUserName(resp.getValue("user_name"));

				list.add(templateDeptApproval);
			}

			message.setData(list);
		}

		return message;
	}

	@RequestMapping(value = "/template/dept/approval", method = RequestMethod.POST)
	@ResponseBody
	public Message addTemplateDeptApproval(
			@RequestParam(value = "configId", required = true) int configId,
			@RequestParam(value = "userIds", required = true) String userIds,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		String[] userIdArr = userIds.split(",");
		for (String uid : userIdArr) {
			ARRequest req = new ARRequest();
			req.setBranchNo(Constants.BRANCH_NO);
			req.setFunctionNo(EFunctionID.TEMPLATE_APPROVAL_ADD.getId());
			req.setParam("i_company_id", user.getCompanyId());
			req.setParam("i_config_id", configId);
			req.setParam("i_user_id", Integer.valueOf(uid));

			ARResponse resp = ARCorrespond.post(null, req);
			if (resp.getErroNo() != 0) {
				log.error(resp.getErrorMsg());
			}
		}
		return this.listTemplateDeptApprovals(configId, session);
	}

	@RequestMapping(value = "/template/dept/approval/{configId}/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message removeTemplateDeptApproval(@PathVariable int configId,
			@PathVariable int userId, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.TEMPLATE_APPROVAL_DELETE.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_config_id", configId);
		req.setParam("i_user_id", userId);

		ARResponse resp = ARCorrespond.post(req);

		return Message.fromResponse(resp);
	}
}
