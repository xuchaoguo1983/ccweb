package com.pengyang.ccweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cjscpool2.ARCorrespond;
import cjscpool2.ARRequest;
import cjscpool2.ARResponse;

import com.pengyang.ccweb.Constants;
import com.pengyang.ccweb.EFunctionID;
import com.pengyang.ccweb.bo.Member;
import com.pengyang.ccweb.bo.MemberAudit;
import com.pengyang.ccweb.bo.MemberAuditResult;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.PageMeta;
import com.pengyang.ccweb.bo.PageWrapper;
import com.pengyang.ccweb.bo.User;
import com.pengyang.ccweb.tools.ExcelReader;

/**
 * 成员管理模块
 * 
 * @author xuchaoguo
 * 
 */
@Controller
public class MemberController {
	private final Logger log = Logger.getLogger(MemberController.class);

	@RequestMapping(value = "/members", method = RequestMethod.GET)
	public String index(Model model) {
		return "member";
	}

	@RequestMapping(value = "/members/{deptId}/{page}", method = RequestMethod.GET)
	@ResponseBody
	public Message list(@PathVariable int deptId, @PathVariable int page,
			@RequestParam(value = "pageRow", defaultValue = "10") int pageRow,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.MEMBER_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_dept_id", deptId);
		req.setParam("i_page", page);
		req.setParam("i_perpage", pageRow);

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			List<Member> list = new ArrayList<Member>();
			PageMeta pageMeta = new PageMeta();
			pageMeta.setPageRow(pageRow);
			pageMeta.setPage(page);

			while (resp.next()) {
				Member member = new Member();

				member.setUserId(Integer.valueOf(resp.getValue("user_id")));
				member.setUserName(resp.getValue("user_name"));
				member.setUserImage(resp.getValue("user_image"));
				member.setMobile(resp.getValue("mobile"));
				member.setEmail(resp.getValue("email"));
				member.setCompanyName(resp.getValue("company_name"));
				member.setPosition(resp.getValue("position"));
				member.setStatus(resp.getValue("status"));
				member.setAddress(resp.getValue("address"));
				member.setOfficeTell(resp.getValue("office_tel"));
				member.setBirthPlace(resp.getValue("birth_place"));

				if (pageMeta.getTotalRows() == 0) {
					pageMeta.setTotalRows(Integer.valueOf(resp
							.getValue("total_rows")));
				}

				list.add(member);
			}

			PageWrapper pw = new PageWrapper();
			pw.setData(list);
			pw.setPage(pageMeta);
			message.setData(pw);
		}

		return message;
	}

	@RequestMapping(value = "/member/{deptId}", method = RequestMethod.POST)
	@ResponseBody
	public Message update(@PathVariable int deptId,
			@ModelAttribute("member") Member member, Model model,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);

		if (StringUtils.isEmpty(member.getUserId())) {
			// add member
			req.setFunctionNo(EFunctionID.MEMBER_CREATE.getId());
			req.setParam("i_dept_id", deptId);
		} else {
			// update member
			req.setFunctionNo(EFunctionID.MEMBER_UPDATE.getId());
			req.setParam("i_user_id", member.getUserId());
		}

		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_user_name", member.getUserName());
		req.setParam("i_mobile", member.getMobile());
		req.setParam("i_email", member.getEmail());
		req.setParam("i_company_name", member.getCompanyName());
		req.setParam("i_position", member.getPosition());
		req.setParam("i_status", member.getStatus());
		req.setParam("i_address", member.getAddress());
		req.setParam("i_office_tel", member.getOfficeTell());
		req.setParam("i_birth_place", member.getBirthPlace());

		ARResponse resp = ARCorrespond.post(null, req);
		if (resp.getErroNo() != 0) {
			// update failed
			return Message.fromResponse(resp);
		} else {
			// refresh the list page
			return this.list(deptId, 1, PageMeta.DEFAULT_PAGE_ROW, session);
		}
	}

	@RequestMapping(value = "/member/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Message view(@PathVariable int userId, Model model,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.MEMBER_VIEW.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_user_id", userId);

		ARResponse resp = ARCorrespond.post(null, req);
		Message message = Message.fromResponse(resp);
		if (resp.getErroNo() == 0) {
			if (resp.next()) {
				Member member = new Member();

				member.setUserId(Integer.valueOf(resp.getValue("user_id")));
				member.setUserName(resp.getValue("user_name"));
				member.setUserImage(resp.getValue("user_image"));
				member.setMobile(resp.getValue("mobile"));
				member.setEmail(resp.getValue("email"));
				member.setCompanyName(resp.getValue("company_name"));
				member.setPosition(resp.getValue("position"));
				member.setStatus(resp.getValue("status"));
				member.setAddress(resp.getValue("address"));
				member.setOfficeTell(resp.getValue("office_tel"));
				member.setBirthPlace(resp.getValue("birth_place"));

				message.setData(member);
			}
		}

		return message;
	}

	@RequestMapping(value = "/member/{deptId}/{userIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message remove(@PathVariable int deptId,
			@PathVariable String userIds, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		String[] userIdArray = userIds.split(",");

		for (String idStr : userIdArray) {
			ARRequest req = new ARRequest();
			req.setBranchNo(Constants.BRANCH_NO);
			req.setFunctionNo(EFunctionID.MEMBER_DELETE.getId());
			req.setParam("i_company_id", user.getCompanyId());
			req.setParam("i_user_id", Integer.valueOf(idStr));

			ARResponse resp = ARCorrespond.post(null, req);
			if (resp.getErroNo() != 0) {
				return Message.fromResponse(resp);
			}
		}

		return this.list(deptId, 1, PageMeta.DEFAULT_PAGE_ROW, session);
	}

	@RequestMapping(value = "/member/{deptId}/to/{targetDeptId}", method = RequestMethod.POST)
	@ResponseBody
	public Message moveDept(@PathVariable int deptId,
			@PathVariable int targetDeptId,
			@RequestParam(value = "userIds", required = true) String userIds,
			Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		String[] userIdArray = userIds.split(",");

		for (String idStr : userIdArray) {
			ARRequest req = new ARRequest();
			req.setBranchNo(Constants.BRANCH_NO);
			req.setFunctionNo(EFunctionID.MEMBER_MOVE.getId());
			req.setParam("i_company_id", user.getCompanyId());
			req.setParam("i_user_id", Integer.valueOf(idStr));
			req.setParam("i_old_dept_id", deptId);
			req.setParam("i_new_dept_id", targetDeptId);

			ARResponse resp = ARCorrespond.post(null, req);
			if (resp.getErroNo() != 0) {
				return Message.fromResponse(resp);
			}
		}

		return this.list(deptId, 1, PageMeta.DEFAULT_PAGE_ROW, session);
	}

	@RequestMapping(value = "/member/upload", method = RequestMethod.POST)
	@ResponseBody
	@CacheEvict(value = "default", key = "'alldepts'")
	public Message upload(
			@RequestParam(value = "file", required = true) MultipartFile file,
			Model model, HttpSession session) {
		Message message = new Message();

		if (file.isEmpty()) {
			message.setCode(-1);
			message.setMessage("未找到文件");
		} else {
			try {
				File convFile = new File(file.getOriginalFilename());
				file.transferTo(convFile);

				List<List<Object>> dataSet = ExcelReader.readExcel(convFile);
				if (dataSet.size() > 1) {
					User user = (User) session.getAttribute("user");
					// it may cost a lot of time when file size is too large
					for (int i = 1; i < dataSet.size(); i++) {
						List<Object> col = dataSet.get(i);

						if (col.size() < 8) {
							log.error("upload member excel has invalid size of column:"
									+ col.size());
							continue;
						}

						ARRequest req = new ARRequest();
						req.setBranchNo(Constants.BRANCH_NO);
						req.setFunctionNo(EFunctionID.MEMBER_UPLOAD.getId());
						req.setParam("i_company_id", user.getCompanyId());
						req.setParam("i_user_name", String.valueOf(col.get(0)));
						req.setParam("i_mobile", String.valueOf(col.get(1)));
						req.setParam("i_office_tel", String.valueOf(col.get(2)));
						req.setParam("i_company_name",
								String.valueOf(col.get(3)));
						req.setParam("i_address", String.valueOf(col.get(4)));
						req.setParam("i_position", String.valueOf(col.get(5)));
						req.setParam("i_birth_place",
								String.valueOf(col.get(6)));
						req.setParam("i_dept_name", String.valueOf(col.get(7)));

						ARResponse resp = ARCorrespond.post(null, req);
						if (resp.getErroNo() != 0) {
							return Message.fromResponse(resp);
						}
					}

				} else {
					message.setCode(-1);
					message.setMessage("文件内容为空.");
				}

			} catch (IOException e) {
				log.error("读取文件出错：", e);
				message.setCode(-1);
				message.setMessage(e.getMessage());
			}
		}

		return message;
	}

	@RequestMapping(value = "/member/audits", method = RequestMethod.GET)
	public String auditIndex(Model model) {
		return "member_audit";
	}

	@RequestMapping(value = "/member/audits/{page}", method = RequestMethod.GET)
	@ResponseBody
	public Message auditlist(@PathVariable int page,
			@RequestParam(value = "pageRow", defaultValue = "10") int pageRow,
			Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.MEMBER_AUDIT_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_user_id", user.getAdminId());
		req.setParam("i_page", page);
		req.setParam("i_perpage", pageRow);

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			List<MemberAudit> list = new ArrayList<MemberAudit>();
			PageMeta pageMeta = new PageMeta();
			pageMeta.setPageRow(pageRow);
			pageMeta.setPage(page);

			while (resp.next()) {
				MemberAudit auditInfo = new MemberAudit();

				auditInfo.setCompanyId(resp.getValue("company_id"));
				auditInfo.setRecordId(resp.getValue("record_id"));
				auditInfo.setUserId(Integer.valueOf(resp.getValue("user_id")));
				auditInfo.setUserName(resp.getValue("user_name"));
				auditInfo.setApplyDate(resp.getValue("apply_date"));
				auditInfo.setApplyTime(resp.getValue("apply_time"));

				list.add(auditInfo);
			}

			PageWrapper pw = new PageWrapper();
			pw.setData(list);
			pw.setPage(pageMeta);
			message.setData(pw);
		}

		return message;
	}

	@RequestMapping(value = "/member/audits/view/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Message auditview(@PathVariable int userId,
			@RequestParam(value = "recordId") String recordId,
			@RequestParam(value = "companyId") String companyId) {
		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.MEMBER_VIEW.getId());
		req.setParam("i_company_id", companyId);
		req.setParam("i_user_id", userId);

		ARResponse resp = ARCorrespond.post(null, req);
		Message message = Message.fromResponse(resp);

		Member[] memberCompareInfo = new Member[2];
		message.setData(memberCompareInfo);
		if (resp.getErroNo() == 0) {
			if (resp.next()) {
				Member member = new Member();

				member.setUserId(Integer.valueOf(resp.getValue("user_id")));
				member.setUserName(resp.getValue("user_name"));
				member.setUserImage(resp.getValue("user_image"));
				member.setMobile(resp.getValue("mobile"));
				member.setEmail(resp.getValue("email"));
				member.setCompanyName(resp.getValue("company_name"));
				member.setPosition(resp.getValue("position"));
				member.setStatus(resp.getValue("status"));
				member.setAddress(resp.getValue("address"));
				member.setOfficeTell(resp.getValue("office_tel"));
				member.setBirthPlace(resp.getValue("birth_place"));

				memberCompareInfo[0] = member;
				// 查询修改记录
				ARRequest req2 = new ARRequest();
				req2.setBranchNo(Constants.BRANCH_NO);
				req2.setFunctionNo(EFunctionID.MEMBER_AUDIT_VIEW.getId());
				req2.setParam("i_company_id", companyId);
				req2.setParam("i_user_id", userId);
				req2.setParam("i_record_id", recordId);

				ARResponse resp2 = ARCorrespond.post(null, req);
				if (resp2.getErroNo() == 0 && resp2.next()) {
					Member member2 = new Member();
					member2.setUserName(resp2.getValue("user_name"));
					member2.setMobile(resp2.getValue("mobile"));
					member2.setOfficeTell(resp2.getValue("office_tel"));
					member2.setEmail(resp2.getValue("email"));
					member2.setBirthPlace(resp2.getValue("address"));
					member2.setCompanyName(resp2.getValue("company_name"));
					member2.setPosition(resp2.getValue("position"));
					member2.setAddress(resp2.getValue("company_addres"));

					memberCompareInfo[1] = member2;
				}

			}
		}

		return message;

	}

	@RequestMapping(value = "/member/audits/verify", method = RequestMethod.POST)
	@ResponseBody
	public Message auditVerify(
			@ModelAttribute("auditResult") MemberAuditResult auditResult) {
		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.MEMBER_AUDIT_VERIFY.getId());
		req.setParam("i_company_id", auditResult.getCompanyId());
		req.setParam("i_record_id", auditResult.getRecordId());
		req.setParam("i_user_id", auditResult.getUserId());
		req.setParam("i_verify_status", auditResult.getVerifyStatus());
		req.setParam("i_verify_desc", auditResult.getVerifyDesc());

		ARResponse resp = ARCorrespond.post(null, req);
		Message message = Message.fromResponse(resp);

		return message;
	}
}
