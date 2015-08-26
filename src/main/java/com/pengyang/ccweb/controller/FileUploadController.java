package com.pengyang.ccweb.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.tools.file.UploadFileResult;
import com.pengyang.ccweb.tools.file.UploadUtil;

@Controller
@RequestMapping(value = "/fs")
public class FileUploadController {
	private final Logger log = Logger.getLogger(ChamberInfoController.class);

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Message upload(@RequestParam(value = "file") MultipartFile file) {
		Message message = new Message();
		if (file.isEmpty()) {
			message.setCode(-1);
			message.setMessage("ÎÄ¼þÎª¿Õ");
		} else {
			try {
				File convFile = new File(file.getOriginalFilename());
				file.transferTo(convFile);

				UploadFileResult result = UploadUtil.upload(convFile);
				message.setCode(result.getError_no());
				message.setMessage(result.getError_info());
				message.setData(result.getUrl());
			} catch (Exception e) {
				log.error("upload failed:", e);
				message.setCode(-1);
				message.setMessage(e.getMessage());
			}
		}

		return message;
	}
}
