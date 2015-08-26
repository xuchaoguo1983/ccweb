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
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.PageMeta;
import com.pengyang.ccweb.bo.PageWrapper;
import com.pengyang.ccweb.bo.User;
import com.pengyang.ccweb.tools.ExcelReader;

/**
 * 成员管理
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

		ARResponse resp = ARCorrespond.post(req);

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
		req.setParam("i_company_name", member.getCompanyName());
		req.setParam("i_position", member.getPosition());
		req.setParam("i_status", member.getStatus());
		req.setParam("i_address", member.getAddress());
		req.setParam("i_office_tel", member.getOfficeTell());
		req.setParam("i_birth_place", member.getBirthPlace());

		ARResponse resp = ARCorrespond.post(null,req);
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

		ARResponse resp = ARCorrespond.post(req);
		Message message = Message.fromResponse(resp);
		if (resp.getErroNo() == 0) {
			if (resp.next()) {
				Member member = new Member();

				member.setUserId(Integer.valueOf(resp.getValue("user_id")));
				member.setUserName(resp.getValue("user_name"));
				member.setUserImage(resp.getValue("user_image"));
				member.setMobile(resp.getValue("mobile"));
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

			ARResponse resp = ARCorrespond.post(req);
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

			ARResponse resp = ARCorrespond.post(req);
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
			message.setMessage("文件为空");
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
					message.setMessage("没有数据.");
				}

			} catch (IOException e) {
				log.error("上传文件失败：", e);
				message.setCode(-1);
				message.setMessage(e.getMessage());
			}
		}

		return message;
	}
}
