package com.pengyang.ccweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import com.pengyang.ccweb.bo.Checkout;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.PageMeta;
import com.pengyang.ccweb.bo.PageWrapper;
import com.pengyang.ccweb.bo.User;

/**
 * ÍâÇÚ¼ÇÂ¼
 * 
 * @author xuchaoguo
 * 
 */
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "checkout";
	}

	@RequestMapping(value = "/list/{page}", method = RequestMethod.POST)
	@ResponseBody
	public Message list(@PathVariable int page,
			@RequestParam(value = "userIds") String userIds,
			@RequestParam(value = "pageRow", defaultValue = "10") int pageRow,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.CHECKOUT_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());

		if (StringUtils.isEmpty(userIds))
			req.setParam("i_user_id", 0);// select all user records
		else
			req.setParam("i_user_id", userIds);
		req.setParam("i_page", page);
		req.setParam("i_perpage", pageRow);

		ARResponse resp = ARCorrespond.post(req);

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			List<Checkout> list = new ArrayList<Checkout>();

			PageMeta pageMeta = new PageMeta();
			pageMeta.setPageRow(pageRow);
			pageMeta.setPage(page);

			while (resp.next()) {
				Checkout checkout = new Checkout();

				checkout.setCheckoutId(Integer.valueOf(resp
						.getValue("outside_id")));
				checkout.setOutPlace(resp.getValue("outside_place"));
				checkout.setUserId(Integer.valueOf(resp
						.getValue("outside_user")));
				checkout.setUserName(resp.getValue("user_name"));
				checkout.setUserPhoto(resp.getValue("user_image"));
				checkout.setOutTime(resp.getValue("outside_time"));
				checkout.setContent(resp.getValue("outside_content"));
				checkout.setClientId(Integer.valueOf(resp.getValue("client_id")));
				checkout.setClientName(resp.getValue("client_name"));
				checkout.setInformUserIds(resp.getValue("inform_user"));
				checkout.setInformUserNames(resp.getValue("inform_user_name"));

				if (pageMeta.getTotalRows() == 0) {
					pageMeta.setTotalRows(Integer.valueOf(resp
							.getValue("total_rows")));
				}

				list.add(checkout);
			}

			PageWrapper pw = new PageWrapper();
			pw.setData(list);
			pw.setPage(pageMeta);
			message.setData(pw);
		}

		return message;
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable int id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.CHECKOUT_VIEW.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_outside_id", id);

		ARResponse resp = ARCorrespond.post(req);
		if (resp.getErroNo() == 0) {
			if (resp.next()) {
				Checkout checkout = new Checkout();

				checkout.setCheckoutId(Integer.valueOf(resp
						.getValue("outside_id")));
				checkout.setOutPlace(resp.getValue("outside_place"));
				checkout.setUserId(Integer.valueOf(resp
						.getValue("outside_user")));
				checkout.setUserName(resp.getValue("user_name"));
				checkout.setUserPhoto(resp.getValue("user_image"));
				checkout.setOutTime(resp.getValue("outside_time"));
				checkout.setContent(resp.getValue("outside_content"));
				checkout.setClientId(Integer.valueOf(resp.getValue("client_id")));
				checkout.setClientName(resp.getValue("client_name"));
				checkout.setPictures(resp.getValue("outside_picture"));
				checkout.setInformUserIds(resp.getValue("inform_user"));
				checkout.setInformUserNames(resp.getValue("inform_user_name"));

				model.addAttribute("checkout", checkout);
			}
		} else {
			model.addAttribute("error", resp.getErrorMsg());
		}

		return "checkout_view";
	}
}
