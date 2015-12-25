package com.pengyang.ccweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
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
import com.pengyang.ccweb.bo.Job;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.User;

@Controller
public class JobController {
	private final Logger log = Logger.getLogger(TemplateController.class);

	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public String index() {
		return "job";
	}

	@RequestMapping(value = "/jobs/{ifbase}", method = RequestMethod.POST)
	@ResponseBody
	public Message list(@PathVariable int ifbase, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.JOB_QUERY.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_if_base", ifbase);

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			List<Job> list = new ArrayList<Job>();

			while (resp.next()) {
				Job job = new Job();
				job.setJobId(resp.getValue("job_id"));
				job.setJobName(resp.getValue("job_name"));
				job.setIfBase(Integer.valueOf(resp.getValue("if_base")));
				job.setJobLevel(Integer.valueOf(resp.getValue("job_level")));
				job.setIfViewBranch(Integer.valueOf(resp
						.getValue("if_view_branch")));

				list.add(job);
			}

			message.setData(list);
		}

		return message;
	}

	@RequestMapping(value = "/job/savelevel", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLevel(@RequestParam(value = "jobLevel") int jobLevel,
			@RequestParam(value = "jobId") String jobId, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.JOB_UPDATE.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_job_id", jobId);
		req.setParam("i_job_level", jobLevel);

		ARResponse resp = ARCorrespond.post(null, req);

		return Message.fromResponse(resp);

	}

	@RequestMapping(value = "/job/branchview", method = RequestMethod.POST)
	@ResponseBody
	public Message saveAuth(@RequestParam(value = "viewBranch") int viewBranch,
			@RequestParam(value = "jobId") String jobId, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.JOB_AUTH_UPDATE.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_job_id", jobId);
		req.setParam("i_if_view_branch", viewBranch);

		ARResponse resp = ARCorrespond.post(null, req);

		return Message.fromResponse(resp);

	}
}
