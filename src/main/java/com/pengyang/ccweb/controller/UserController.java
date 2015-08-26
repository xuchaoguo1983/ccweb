package com.pengyang.ccweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cjscpool2.ARCorrespond;
import cjscpool2.ARRequest;
import cjscpool2.ARResponse;

import com.pengyang.ccweb.Constants;
import com.pengyang.ccweb.EFunctionID;
import com.pengyang.ccweb.bo.Member;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.User;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	@ResponseBody
	public Message view(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user.getMember() != null) {
			Message message = new Message();
			message.setData(user.getMember());
			return message;
		}

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.MEMBER_VIEW.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_user_id", user.getAdminId());

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

				user.setMember(member);
			}
		}

		return message;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Message save(@ModelAttribute("member") Member member,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		// update member
		req.setFunctionNo(EFunctionID.MEMBER_UPDATE.getId());
		req.setParam("i_user_id", member.getUserId());

		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_user_name", member.getUserName());
		req.setParam("i_mobile", member.getMobile());
		req.setParam("i_company_name", member.getCompanyName());
		req.setParam("i_position", member.getPosition());
		req.setParam("i_status", member.getStatus());
		req.setParam("i_address", member.getAddress());
		req.setParam("i_office_tel", member.getOfficeTell());
		req.setParam("i_birth_place", member.getBirthPlace());

		ARResponse resp = ARCorrespond.post(req);
		if (resp.getErroNo() == 0) {
			user.setMember(member);
		}

		Message message = Message.fromResponse(resp);
		message.setData(member);

		return message;
	}
}
