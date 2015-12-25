package com.pengyang.ccweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cjscpool2.ARCorrespond;
import cjscpool2.ARRequest;
import cjscpool2.ARResponse;

import com.pengyang.ccweb.Constants;
import com.pengyang.ccweb.EFunctionID;
import com.pengyang.ccweb.bo.Login;
import com.pengyang.ccweb.bo.LoginQRCode;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.User;
import com.pengyang.ccweb.tools.QRCodeGenerator;


/**
 * ��½�˳�
 * 
 * @author xuchaoguo
 * 
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session) {
		// check if user is already in
		if (session.getAttribute("user") != null) {
			return "redirect:/depts";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index2(HttpSession session) {
		return index(session);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("login") Login login, Model model,
			HttpSession session) {
		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.LOGIN.getId());
		req.setParam("i_admin_mobile", "13926466986");
		req.setParam("i_admin_pwd", "123456");

		ARResponse resp = ARCorrespond.post(null, req);

		if (resp.getErroNo() != 0) {
			// login failed
			model.addAttribute("error", resp.getErrorMsg());
			return "index";
		} else {
			// login success
			if (resp.next()) {
				User user = new User();

				user.setCompanyId(resp.getValue("company_id"));
				user.setCompanyName(resp.getValue("company_name"));
				user.setAdminId(Integer.valueOf(resp.getValue("user_id")));
				user.setAdminName(resp.getValue("admin_name"));
				user.setAdminImage(resp.getValue("admin_image"));
				user.setAdminMobile(resp.getValue("admin_mobile"));
				user.setJobName(resp.getValue("job_name"));

				session.setAttribute("user", user);
			}

			return "redirect:/depts";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@CacheEvict(value = "default", allEntries = true)
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("user");
		return "redirect:/";
	}

	@RequestMapping(value = "/login/qrcode", method = RequestMethod.GET)
	public void qrcode(HttpServletRequest request, HttpServletResponse response) {
		LoginQRCode code = new LoginQRCode();
		code.setAuthType(1);
		code.setClient(request.getRemoteAddr());
		code.setCode(UUID.randomUUID().toString());
		code.setCreateTime(System.currentTimeMillis());

		String path = request.getServletContext().getRealPath("/upload/temp");

		try {
			File file = QRCodeGenerator.generate("login_qrcode",
					code.toString(), path, 230, 230);
			
			System.out.println("code:" + code.toString());

			FileInputStream inputStream = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			inputStream.read(data);
			inputStream.close();

			response.setContentType("image/png");

			OutputStream stream = response.getOutputStream();
			stream.write(data);
			stream.flush();
			stream.close();

			HttpSession session = request.getSession();
			session.setAttribute("login_qrcode", code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/login/qrcode", method = RequestMethod.POST)
	@ResponseBody
	public Message qrcodeLogin(HttpSession session) {
		Object object = session.getAttribute("login_qrcode");
		if (object == null) {
			Message message = new Message();
			message.setCode(-9999);
			message.setMessage("������ʧЧ");
			return message;
		}

		LoginQRCode code = (LoginQRCode) object;
		if (!code.isActive()) {
			Message message = new Message();
			message.setCode(-9999);
			message.setMessage("������ʧЧ");
			return message;
		}

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.QR_LOGIN.getId());
		req.setParam("i_require_token", code.getCode());
		req.setParam("i_client", code.getClient());
		req.setParam("i_auth_type", code.getAuthType());

		ARResponse resp = ARCorrespond.post(null, req);
		if (resp.getErroNo() == 0) {
			// login success
			if (resp.next()) {
				User user = new User();

				user.setCompanyId(resp.getValue("company_id"));
				user.setCompanyName(resp.getValue("company_name"));
				user.setAdminId(Integer.valueOf(resp.getValue("user_id")));
				user.setAdminName(resp.getValue("admin_name"));
				user.setAdminImage(resp.getValue("admin_image"));
				user.setAdminMobile(resp.getValue("admin_mobile"));
				user.setJobName(resp.getValue("job_name"));

				session.setAttribute("user", user);
			}
		}

		return Message.fromResponse(resp);
	}
}
