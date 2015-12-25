package com.pengyang.ccweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import com.pengyang.ccweb.bo.Announcement;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.PageMeta;
import com.pengyang.ccweb.bo.PageWrapper;
import com.pengyang.ccweb.bo.User;
import com.pengyang.ccweb.tools.ImgDataUtil;

/**
 * 公告管理模块
 * @author xuchaoguo
 *
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "announcement";
	}

	@RequestMapping(value = "/list/{status}/{page}", method = RequestMethod.GET)
	@ResponseBody
	public Message list(@PathVariable int status, @PathVariable int page,
			@RequestParam(value = "beginDate") int beginDate,
			@RequestParam(value = "endDate") int endDate,
			@RequestParam(value = "pageRow", defaultValue = "10") int pageRow,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.ANNOUNCEMENT_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_announcement_status", status);
		req.setParam("i_begin_date", beginDate);
		req.setParam("i_end_date", endDate);
		req.setParam("i_page", page);
		req.setParam("i_perpage", pageRow);

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			List<Announcement> list = new ArrayList<Announcement>();

			PageMeta pageMeta = new PageMeta();
			pageMeta.setPageRow(pageRow);
			pageMeta.setPage(page);

			while (resp.next()) {
				Announcement ann = new Announcement();

				ann.setAnnouncementId(Integer.valueOf(resp
						.getValue("announcement_id")));
				ann.setTitle(resp.getValue("announcement_title"));
				ann.setPublisherId(Integer.valueOf(resp
						.getValue("publish_person")));
				ann.setPublisherName(resp.getValue("user_name"));
				ann.setPublishTime(resp.getValue("publish_time"));
				ann.setStatus(Integer.valueOf(resp
						.getValue("announcement_status")));

				if (pageMeta.getTotalRows() == 0) {
					pageMeta.setTotalRows(Integer.valueOf(resp
							.getValue("total_rows")));
				}

				list.add(ann);
			}

			PageWrapper pw = new PageWrapper();
			pw.setData(list);
			pw.setPage(pageMeta);
			message.setData(pw);
		}

		return message;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String add() {
		return "announcement_edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable int id, Model model, HttpSession session) {
		this.view(id, model, session);
		return "announcement_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Message save(
			@ModelAttribute("announcement") Announcement announcement,
			Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);

		if (announcement.getAnnouncementId() != null) {
			req.setFunctionNo(EFunctionID.ANNOUNCEMENT_UPDATE.getId());
			req.setParam("i_announcement_id", announcement.getAnnouncementId());
		} else {
			req.setFunctionNo(EFunctionID.ANNOUNCEMENT_CREATE.getId());
		}

		String imageFilePath = session.getServletContext().getRealPath(
				"/upload/temp");

		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_announcement_title", announcement.getTitle());
		req.setParam("i_announcement_content",
				ImgDataUtil.parse(announcement.getContent(), imageFilePath));
		req.setParam("i_user_id", user.getAdminId());
		req.setParam("i_announcement_type", announcement.getTarget());
		req.setParam("i_announcement_status", announcement.getStatus());

		String targetIds = announcement.getTargetIds();
		if (StringUtils.isEmpty(targetIds))
			req.setParam("i_announcement_object", "-1");
		else
			req.setParam("i_announcement_object", targetIds);

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);
		return message;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message delete(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.ANNOUNCEMENT_DELETE.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_announcement_id", id);
		req.setParam("i_user_id", user.getAdminId());

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);
		return message;
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public Message verify(@RequestParam(value = "verifyId") int verifyId,
			@RequestParam(value = "verifyStatus") int verifyStatus,
			@RequestParam(value = "verifyDesc") String verifyDesc,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.ANNOUNCEMENT_VERIFY.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_announcement_id", verifyId);
		req.setParam("i_user_id", user.getAdminId());
		req.setParam("i_status", verifyStatus);
		req.setParam("i_verify_desc", verifyDesc);

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);
		return message;
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable int id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.ANNOUNCEMENT_VIEW.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_announcement_id", id);

		ARResponse resp = ARCorrespond.post(null, req);

		if (resp.getErroNo() == 0) {
			if (resp.next()) {
				Announcement announcement = new Announcement();
				announcement.setAnnouncementId(Integer.valueOf(resp
						.getValue("announcement_id")));
				announcement.setTitle(resp.getValue("announcement_title"));
				announcement.setContent(resp.getValue("announcement_content"));
				announcement.setPublisherId(Integer.valueOf(resp
						.getValue("publish_person")));
				announcement.setPublisherName(resp.getValue("user_name"));
				announcement.setPublishTime(resp.getValue("publish_time"));
				announcement.setStatus(Integer.valueOf(resp
						.getValue("announcement_status")));

				if (!StringUtils.isEmpty(resp.getValue("verify_person"))) {
					announcement.setVerifyUserId(Integer.valueOf(resp
							.getValue("verify_person")));
					announcement.setVerifyUserName(resp
							.getValue("verify_user_name"));
					announcement.setVerifyTime(resp.getValue("verify_time"));
					announcement.setVerifyDesc(resp.getValue("verify_desc"));
				}

				announcement.setTarget(Integer.valueOf(resp
						.getValue("object_type")));
				announcement.setTargetIds(resp.getValue("object_id"));
				announcement.setTargetNames(resp.getValue("object_name"));

				model.addAttribute("announcement", announcement);
			}
		} else {
			model.addAttribute("error", resp.getErrorMsg());
		}

		return "announcement_view";
	}
}
