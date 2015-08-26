package com.pengyang.ccweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.pengyang.ccweb.bo.ChamberInfo;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.PageMeta;
import com.pengyang.ccweb.bo.PageWrapper;
import com.pengyang.ccweb.bo.User;

/**
 * 商会信息
 * 
 * @author xuchaoguo
 * 
 */
@Controller
@RequestMapping("/chamberinfo/{category}")
public class ChamberInfoController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@PathVariable int category,
			@RequestParam(value = "type", defaultValue = "-1") int type,
			Model model) {
		model.addAttribute("category", category);
		model.addAttribute("type", type);
		return "chamberinfo";
	}

	@RequestMapping(value = "/list/{type}/{page}", method = RequestMethod.GET)
	@ResponseBody
	public Message list(@PathVariable int type, @PathVariable int page,
			@RequestParam(value = "pageRow", defaultValue = "10") int pageRow,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.CHAMBERINFO_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_type_id", type);
		req.setParam("i_page", page);
		req.setParam("i_perpage", pageRow);

		ARResponse resp = ARCorrespond.post(req);

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			List<ChamberInfo> list = new ArrayList<ChamberInfo>();

			PageMeta pageMeta = new PageMeta();
			pageMeta.setPageRow(pageRow);
			pageMeta.setPage(page);

			while (resp.next()) {
				ChamberInfo info = new ChamberInfo();
				info.setInfoId(Integer.valueOf(resp.getValue("info_id")));
				info.setTitle(resp.getValue("info_title"));
				info.setPublisherId(Integer.valueOf(resp
						.getValue("publish_user")));
				info.setPublisherName(resp.getValue("user_name"));
				info.setPublishTime(resp.getValue("publish_time"));
				info.setIsHot(Integer.valueOf(resp.getValue("if_hot")));

				if (pageMeta.getTotalRows() == 0) {
					pageMeta.setTotalRows(Integer.valueOf(resp
							.getValue("total_rows")));
				}

				list.add(info);
			}

			PageWrapper pw = new PageWrapper();
			pw.setData(list);
			pw.setPage(pageMeta);
			message.setData(pw);
		}

		return message;
	}

	@RequestMapping(value = "/edit/{type}", method = RequestMethod.GET)
	public String add(@PathVariable int category, @PathVariable int type,
			Model model, HttpSession session) {
		model.addAttribute("category", category);
		model.addAttribute("type", type);
		return "chamberinfo_edit";
	}

	@RequestMapping(value = "/edit/{type}/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable int category, @PathVariable int type,
			@PathVariable int id, Model model, HttpSession session) {
		model.addAttribute("category", category);
		model.addAttribute("type", type);
		this.view(category, type, id, model, session);

		return "chamberinfo_edit";
	}

	@RequestMapping(value = "/save/{type}", method = RequestMethod.POST)
	@ResponseBody
	public Message save(@PathVariable int type,
			@ModelAttribute("chamberInfo") ChamberInfo chamberInfo,
			Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);

		if (chamberInfo.getInfoId() == null) {
			req.setFunctionNo(EFunctionID.CHAMBERINFO_CREATE.getId());
			req.setParam("i_type_id", type);
			req.setParam("i_user_id", user.getAdminId());
		} else {
			req.setFunctionNo(EFunctionID.CHAMBERINFO_UPDATE.getId());
			req.setParam("i_info_id", chamberInfo.getInfoId());
		}

		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_info_title", chamberInfo.getTitle());
		req.setParam("i_info_content", chamberInfo.getContent());
		req.setParam("i_if_hot", chamberInfo.getIsHot());

		if (chamberInfo.getIsHot() > 0) {
			req.setParam("i_hot_picture", chamberInfo.getHotImage());
		} else {
			req.setParam("i_hot_picture", "");
		}

		ARResponse resp = ARCorrespond.post(req);

		return Message.fromResponse(resp);
	}

	@RequestMapping(value = "/view/{type}/{id}", method = RequestMethod.GET)
	public String view(@PathVariable int category, @PathVariable int type,
			@PathVariable int id, Model model, HttpSession session) {

		model.addAttribute("category", category);
		model.addAttribute("type", type);

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.CHAMBERINFO_VIEW.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_info_id", id);

		ARResponse resp = ARCorrespond.post(req);
		if (resp.getErroNo() == 0) {
			if (resp.next()) {
				ChamberInfo chamberInfo = new ChamberInfo();

				chamberInfo
						.setInfoId(Integer.valueOf(resp.getValue("info_id")));
				chamberInfo.setTitle(resp.getValue("info_title"));
				chamberInfo.setContent(resp.getValue("info_content"));
				chamberInfo.setPublisherId(Integer.valueOf(resp
						.getValue("publish_user")));
				chamberInfo.setPublisherName(resp.getValue("user_name"));
				chamberInfo.setPublishTime(resp.getValue("publish_time"));
				chamberInfo.setReadTimes(Integer.valueOf(resp
						.getValue("read_times")));
				chamberInfo.setIsHot(Integer.valueOf(resp.getValue("if_hot")));
				chamberInfo.setHotImage(resp.getValue("hot_picture"));

				model.addAttribute("chamberInfo", chamberInfo);
			}
		} else {
			model.addAttribute("error", resp.getErrorMsg());
		}

		return "chamberinfo_view";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message delete(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.CHAMBERINFO_DELETE.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_info_id", id);

		ARResponse resp = ARCorrespond.post(req);
		return Message.fromResponse(resp);
	}
}