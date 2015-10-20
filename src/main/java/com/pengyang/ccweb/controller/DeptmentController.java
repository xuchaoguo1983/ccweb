package com.pengyang.ccweb.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.pengyang.ccweb.bo.Dept;
import com.pengyang.ccweb.bo.DeptSelect;
import com.pengyang.ccweb.bo.Message;
import com.pengyang.ccweb.bo.User;

/**
 * ���Ź���
 * 
 * @author xuchaoguo
 * 
 */
@Controller
public class DeptmentController {

	@RequestMapping(value = "/depts", method = RequestMethod.GET)
	public String index(Model model) {
		return "dept";
	}

	@RequestMapping(value = "/depts/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public Message list(@PathVariable int pid, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.DEPT_LIST.getId());
		req.setParam("i_pre_dept_id", pid);
		req.setParam("i_company_id", user.getCompanyId());

		ARResponse resp = ARCorrespond.post(null, req);

		List<Dept> list = new ArrayList<Dept>();

		Message message = Message.fromResponse(resp);

		if (resp.getErroNo() == 0) {
			while (resp.next()) {
				Dept dept = new Dept();

				dept.setDeptId(Integer.valueOf(resp.getValue("dept_id")));
				dept.setDeptName(resp.getValue("dept_name"));
				dept.setIndex(Integer.valueOf(resp.getValue("index_no")));

				list.add(dept);
			}

			Collections.sort(list, new Comparator<Dept>() {

				public int compare(Dept o1, Dept o2) {
					return o1.getIndex() > o2.getIndex() ? 1 : -1;
				}

			});

			message.setData(list);
		}

		return message;
	}

	@RequestMapping(value = "/dept", method = RequestMethod.POST)
	@ResponseBody
	@Caching(evict = { @CacheEvict(value = "default", key = "'alldepts'"),
			@CacheEvict(value = "default", key = "'alljobs'") })
	public Message add(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "pid", required = true) String pid,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.DEPT_CREATE.getId());
		req.setParam("i_pre_dept_id", pid);
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_dept_name", name);

		ARResponse resp = ARCorrespond.post(null, req);

		Message message = Message.fromResponse(resp);
		if (resp.getErroNo() == 0) {
			if (resp.next()) {
				Dept dept = new Dept();
				dept.setDeptId(Integer.valueOf(resp.getValue("dept_id")));
				dept.setDeptName(name);
				dept.setIndex(dept.getDeptId());
				message.setData(dept);
			}
		}

		return message;
	}

	@RequestMapping(value = "/dept/{deptId}", method = RequestMethod.POST)
	@ResponseBody
	@Caching(evict = { @CacheEvict(value = "default", key = "'alldepts'"),
			@CacheEvict(value = "default", key = "'alljobs'") })
	public Message update(@PathVariable int deptId,
			@RequestParam(value = "name", required = true) String name,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.DEPT_UPDATE.getId());
		req.setParam("i_dept_id", deptId);
		req.setParam("i_company_id", user.getCompanyId());
		req.setParam("i_dept_name", name);

		ARResponse resp = ARCorrespond.post(null, req);

		return Message.fromResponse(resp);
	}

	@RequestMapping(value = "/dept/{deptId}", method = RequestMethod.DELETE)
	@ResponseBody
	@Caching(evict = { @CacheEvict(value = "default", key = "'alldepts'"),
			@CacheEvict(value = "default", key = "'alljobs'") })
	public Message remove(@PathVariable int deptId, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.DEPT_DELETE.getId());
		req.setParam("i_dept_id", deptId);
		req.setParam("i_company_id", user.getCompanyId());

		ARResponse resp = ARCorrespond.post(null, req);

		return Message.fromResponse(resp);
	}

	@RequestMapping(value = "/dept/move", method = RequestMethod.POST)
	@ResponseBody
	@Caching(evict = { @CacheEvict(value = "default", key = "'alldepts'"),
			@CacheEvict(value = "default", key = "'alljobs'") })
	public Message move(
			@RequestParam(value = "deptId1", required = true) int deptId1,
			@RequestParam(value = "deptId2", required = true) int deptId2,
			@RequestParam(value = "deptIndex1", required = true) int deptIndex1,
			@RequestParam(value = "deptIndex2", required = true) int deptIndex2,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.DEPT_MOVE.getId());
		req.setParam("i_dept_id1", deptId1);
		req.setParam("i_dept_id2", deptId2);
		req.setParam("i_index_no1", deptIndex1);
		req.setParam("i_index_no2", deptIndex2);
		req.setParam("i_company_id", user.getCompanyId());

		ARResponse resp = ARCorrespond.post(null, req);

		return Message.fromResponse(resp);
	}

	@RequestMapping(value = "/depts/select", method = RequestMethod.GET)
	@ResponseBody
	@Cacheable(value = "default", key = "'alldepts'")
	public List<DeptSelect> select(HttpSession session) {
		User user = (User) session.getAttribute("user");

		ARRequest req = new ARRequest();
		req.setBranchNo(Constants.BRANCH_NO);
		req.setFunctionNo(EFunctionID.DEPT_ALL.getId());
		req.setParam("i_company_id", user.getCompanyId());

		ARResponse resp = ARCorrespond.post(null, req);

		Map<Integer, List<DeptSelect>> map = new HashMap<Integer, List<DeptSelect>>();

		if (resp.getErroNo() == 0) {
			while (resp.next()) {
				DeptSelect dept = new DeptSelect();

				dept.setV(Integer.valueOf(resp.getValue("dept_id")));
				dept.setN(resp.getValue("dept_name"));
				dept.setI(Integer.valueOf(resp.getValue("index_no")));

				List<DeptSelect> s = map.get(dept.getV());
				if (s == null) {
					s = new ArrayList<DeptSelect>();
					map.put(dept.getV(), s);
				}

				dept.setS(s);

				Integer parentDeptId = Integer.valueOf(resp
						.getValue("admin_dept_id"));
				// add dept to parent sub list
				List<DeptSelect> subList = map.get(parentDeptId);
				if (subList == null) {
					subList = new ArrayList<DeptSelect>();
					map.put(parentDeptId, subList);
				}

				subList.add(dept);
			}

			Iterator<Integer> keyItor = map.keySet().iterator();
			while (keyItor.hasNext()) {
				List<DeptSelect> list = map.get(keyItor.next());
				if (list.size() > 0) {
					Collections.sort(list, new Comparator<DeptSelect>() {
						public int compare(DeptSelect o1, DeptSelect o2) {
							return o1.getI() > o2.getI() ? 1 : -1;
						}

					});
				}
			}

		}

		return map.get(Constants.ROOT_DEPT_ID);
	}
}
