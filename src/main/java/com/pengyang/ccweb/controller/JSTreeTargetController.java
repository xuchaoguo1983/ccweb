package com.pengyang.ccweb.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cjscpool2.ARCorrespond;
import cjscpool2.ARRequest;
import cjscpool2.ARResponse;

import com.pengyang.ccweb.Constants;
import com.pengyang.ccweb.EFunctionID;
import com.pengyang.ccweb.bo.DeptSelect;
import com.pengyang.ccweb.bo.JsTreeNode;
import com.pengyang.ccweb.bo.User;

/**
 * JsTree服务器数据管理
 * @author xuchaoguo
 *
 */
@Controller
@RequestMapping(value = "/jsTree")
public class JSTreeTargetController {

	@Resource
	DeptmentController deptmentController;

	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	@ResponseBody
	@Cacheable(value = "default", key = "'alljobs'")
	public List<JsTreeNode> listAllJobs(HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.JOB_LIST.getId());
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_page", 1);
		req.setParam("i_perpage", Integer.MAX_VALUE);// get all jobs

		ARResponse resp = ARCorrespond.post(req);

		List<JsTreeNode> list = new ArrayList<JsTreeNode>();

		if (resp.getErroNo() == 0) {
			while (resp.next()) {
				JsTreeNode node = new JsTreeNode();

				node.setId(resp.getValue("job_id"));
				node.setText(resp.getValue("job_name"));
				node.setIndex(Integer.valueOf(resp.getValue("job_level")));

				list.add(node);
			}

			Collections.sort(list, new Comparator<JsTreeNode>() {

				public int compare(JsTreeNode o1, JsTreeNode o2) {
					return o1.getIndex() > o2.getIndex() ? -1 : 1;
				}

			});
		}

		return list;
	}

	@RequestMapping(value = "/depts", method = RequestMethod.GET)
	@ResponseBody
	public List<JsTreeNode> listAllDepts(HttpSession session) {
		List<JsTreeNode> nodeList = new ArrayList<JsTreeNode>();

		List<DeptSelect> list = deptmentController.select(session);
		if (list != null) {
			for (DeptSelect ds : list)
				nodeList.add(ds.toJsTreeNode());
		}

		return nodeList;
	}
}
