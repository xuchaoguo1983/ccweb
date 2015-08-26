/**
 * 部门管理模块
 */

function loadDepts(pid, plv) {
	$.ajax({
		url : "./depts/" + pid,
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				var panel = $("#deptpanel_" + plv);
				var listgroup = panel.children(".list-group:first");

				listgroup.html('');
				var data = resp.data;
				for (var i = 0; i < data.length; i++) {
					addDeptItem(listgroup, data[i], plv);
				}
			} else {
				bootbox.alert(resp.message);
			}
		},
		error : function(data, status, er) {
			loadUI.hidePleaseWait();
			bootbox.alert("数据请求出错：" + er);
		}
	});
}

function createDept(plv) {
	var panel = $("#deptpanel_" + plv);

	// 查找是否已存在input
	var deptInput = panel.find(".newdeptItem:first");
	if (deptInput.length > 0) {
		deptInput[0].focus();
		return false;
	}

	var listgroup = panel.children(".list-group:first");
	listgroup
			.append('<a class="list-group-item"><input class="deptinput newdeptItem" type="text"/></a>');

	deptInput = panel.find(".newdeptItem:first");

	deptInput.focus();
	deptInput.on("blur keydown", function(event) {
		// 移除焦点时，自动保存
		if (event.type == 'blur' || event.which == 13) {
			var val = $.trim(deptInput.val());
			var preDeptId = getPreDeptId(plv);
			if (val.length > 0 && preDeptId != null) {
				$.ajax({
					url : "./dept",
					data : {
						"pid" : preDeptId,
						"name" : val
					},
					type : 'POST',
					beforeSend : function() {
						loadUI.showPleaseWait();
					},
					success : function(resp) {
						loadUI.hidePleaseWait();

						if (resp.code == 0) {
							deptInput.parent().remove();
							addDeptItem(listgroup, resp.data, plv);
						} else {
							bootbox.alert(resp.message);
						}
					},
					error : function(data, status, er) {
						loadUI.hidePleaseWait();
						deptInput.parent().remove();
						bootbox.alert("数据请求出错：" + er);
					}
				});
			} else {
				deptInput.parent().remove();
			}
		}
	});
}

// 插入行
function addDeptItem(listgroup, data, plv) {
	listgroup
			.append('<a id="'
					+ data.deptId
					+ '" index="'
					+ data.index
					+ '" onclick="selectDept('
					+ data.deptId
					+ ','
					+ plv
					+ ')" class="dept-item list-group-item"><span>'
					+ data.deptName
					+ '</span><input class="deptinput" style="display:none;" type="text"/><span></span></a>');
	var item = $("#" + data.deptId);
	var span = item.children("span:last");

	item
			.hover(
					function() {
						span
								.html('<i class="fa fa-angle-double-up fa-fw" title="上移"></i><i class="fa fa-angle-double-down fa-fw" title="下移"></i><i class="fa fa-pencil fa-fw" title="编辑"></i><i class="fa fa-trash-o fa-fw" title="删除"></i>');

						// handler
						span.children(".fa-angle-double-up:first").click(
								function(e) {
									moveDeptUp(data.deptId);
									return false;
								});
						span.children(".fa-angle-double-down:first").click(
								function(e) {
									moveDeptDown(data.deptId);
									return false;
								});
						span.children(".fa-pencil:first").click(function(e) {
							editDept(data.deptId);
							return false;
						});
						span.find(".fa-trash-o:first").click(function(e) {
							removeDept(data.deptId);
							return false;
						});
					}, function() {
						span.html('');
					});
}

function selectDept(deptId, plv) {
	if (plv >= 3)
		return false;

	var item = $('#' + deptId);
	// 首先移除其他active节点标示
	item.parent().children(".active").each(function(index, element) {
		$(element).removeClass("active");
	});

	item.addClass("active");

	var subPanel = $("#deptpanel_" + (plv + 1));
	if (subPanel.length > 0) {
		subPanel.removeClass("hide");
		// 加载部门列表
		loadDepts(deptId, plv + 1);
		// 隐藏子节点
		hideSubPanels(plv + 1);
	}
}

function getPreDeptId(plv) {
	if (plv <= 1)
		return ROOT_DEPT_ID;
	// 找到父节点中选中的dept
	var panel = $("#deptpanel_" + (plv - 1));
	var listgroup = panel.children(".list-group:first");
	var activeItem = listgroup.children(".active:first");
	if (activeItem.length == 0) {
		alert("没有选中的上一级部门，请刷新重试");
		return null;
	}

	return Number(activeItem.attr("id"));
}

function hideSubPanels(plv) {
	// 隐藏子节点
	for (var i = plv + 1; i < 100; i++) {
		var p = $("#deptpanel_" + (i));
		if (p.length > 0 && !p.hasClass("hide")) {
			p.addClass("hide");
		} else {
			break;
		}
	}
}

function moveDeptUp(deptId) {
	var curItem = $("#" + deptId);
	var preItem = curItem.prev();
	if (preItem.length > 0) {
		moveDepts(preItem, curItem);
	}
}

function moveDeptDown(deptId) {
	var curItem = $("#" + deptId);
	var nextItem = curItem.next();
	if (nextItem.length > 0) {
		moveDepts(curItem, nextItem);
	}
}

function moveDepts(item1, item2) {
	var index1 = item1.attr("index");
	var index2 = item2.attr("index");

	// 提交更改
	$.ajax({
		url : "./dept/move",
		data : {
			"deptId1" : item1.attr("id"),
			"deptId2" : item2.attr("id"),
			"deptIndex1" : index1,
			"deptIndex2" : index2,
		},
		type : 'POST',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				item1.insertAfter(item2);

				item1.attr("index", index2);
				item2.attr("index", index1);
			} else {
				bootbox.alert(resp.message);
			}
		},
		error : function(data, status, er) {
			loadUI.hidePleaseWait();
			bootbox.alert("数据请求出错：" + er);
		}
	});
}

function editDept(deptId) {
	var span = $("#" + deptId + " span:first");
	var deptInput = span.siblings(".deptinput");
	span.hide();
	deptInput.show().val(span.text()).focus();

	deptInput.on("blur keydown", function(event) {
		if (event.type == 'blur' || event.which == 13) {
			var name = $.trim(deptInput.val());
			if (name == span.text()) {
				span.show();
				deptInput.hide();
				return;
			}

			// 提交更改
			$.ajax({
				url : "./dept/" + deptId,
				data : {
					"name" : name
				},
				type : 'POST',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						span.show();
						deptInput.hide();
						span.text(deptInput.val());
					} else {
						bootbox.alert(resp.message);
					}
				},
				error : function(data, status, er) {
					loadUI.hidePleaseWait();
					bootbox.alert("数据请求出错：" + er);
				}
			});
		}
	});
}

function removeDept(deptId) {
	bootbox.confirm("确定要删除该部门么？", function(ret) {
		if (ret == true) {
			$.ajax({
				url : "./dept/" + deptId,
				data : {},
				type : 'DELETE',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						var item = $('#' + deptId);
						// 检查是否有子节点展开
						if (item.hasClass("active")) {
							var panel = item.parent().parent();
							var plv = panel.attr("id").substr(10);
							// 隐藏下一级部门列表
							hideSubPanels(Number(plv));
						}

						item.remove();
					} else {
						bootbox.alert(resp.message);
					}
				},
				error : function(data, status, er) {
					loadUI.hidePleaseWait();
					bootbox.alert("数据请求出错：" + er);
				}
			});
		}
	});
}

$(function() {
	// -1 is the root dept id
	loadDepts(ROOT_DEPT_ID, 1);
});