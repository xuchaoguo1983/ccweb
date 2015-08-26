/**
 * 审批模版模块
 */

$(function() {
	$('[data-toggle="tooltip"]').tooltip();

	$('#selectedMembers').tagsinput({
		itemValue : 'value',
		itemText : 'text',
	});

	$('#templateform').validator().on('submit', function(e) {
		if (e.isDefaultPrevented()) {
			// validate failed
		} else {
			$.ajax({
				url : "./template",
				type : 'POST',
				data : $('#templateform').serialize(),
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						$('#templateModel').modal('hide');
						// set template list
						$("#templateListGroup").html('');
						for (var i = 0; i < resp.data.length; i++) {
							addTemplateItem(resp.data[i]);
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

			return false;
		}
	});

	$("#saveTemplateBtn").click(function() {
		$('#templateform').submit();
	});

	$("#addTemplateDeptBtn").click(function() {
		var selDeptId = getSelectedDept(true);
		if (selDeptId == null) {
			bootbox.alert('请选择适用部门');
			return false;
		}

		if ($("a[dept='" + selDeptId + "']").length > 0) {
			bootbox.alert('该部门已配置');
			return false;
		}

		// 提交更改
		$.ajax({
			url : "./template/dept",
			data : {
				'templateId' : getSelectedTemplate(),
				'deptId' : selDeptId
			},
			type : 'POST',
			beforeSend : function() {
				loadUI.showPleaseWait();
			},
			success : function(resp) {
				loadUI.hidePleaseWait();
				if (resp.code == 0) {
					$('#templateDeptModel').modal('hide');
					// set template list
					$("#templateDeptListGroup").html('');
					for (var i = 0; i < resp.data.length; i++) {
						addTemplateDeptItem(resp.data[i]);
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
	});

	$("#addTemplateDeptApprovalBtn").click(function() {
		var selectedMemberIds = $("#selectedMembers").val();
		if (selectedMemberIds.length == 0) {
			bootbox.alert('请选择要添加的审批人');
			return false;
		}

		// 提交
		$.ajax({
			url : "./template/dept/approval",
			type : 'POST',
			data : {
				"configId" : getSelectedDeptConfig(),
				"userIds" : selectedMemberIds
			},
			beforeSend : function() {
				loadUI.showPleaseWait();
			},
			success : function(resp) {
				loadUI.hidePleaseWait();
				if (resp.code == 0) {
					$('#templateDeptApprovalModel').modal('hide');
					$("#templateDeptApprovalListGroup").html('');
					// _templateDeptApprovals = resp.data;
					for (var i = 0; i < resp.data.length; i++) {
						addTemplateDeptApprovalItem(resp.data[i]);
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
	});

	// load all templates
	listTemplates();
	// init template type list
	initSelectOptions('templateType', TEMPLATE_TYPE_MAP);
});

function listTemplates() {
	$.ajax({
		url : "./templates",
		type : 'POST',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				// set template list
				$("#templateListGroup").html('');
				for (var i = 0; i < resp.data.length; i++) {
					addTemplateItem(resp.data[i]);
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

function addTemplateItem(data) {
	$("#templateListGroup").append(
			'<a id="t_' + data.templateId + '" onclick="selectTemplate('
					+ data.templateId
					+ ')" class="dept-item list-group-item"><span>' + '【'
					+ TEMPLATE_TYPE_MAP[data.templateType] + '】'
					+ data.templateName + '</span><span></span></a>');
	var item = $("#t_" + data.templateId);
	var span = item.children("span:last");

	item
			.hover(
					function() {
						span
								.html('<i class="fa fa-pencil fa-fw" title="编辑"></i><i class="fa fa-trash-o fa-fw" title="删除"></i>');

						// handler
						span.children(".fa-pencil:first").click(function(e) {
							editTemplate(data);
							return false;
						});
						span.find(".fa-trash-o:first").click(function(e) {
							removeTemplate(data.templateId);
							return false;
						});
					}, function() {
						span.html('');
					});
}

function createTemplate() {
	$('#templateform')[0].reset();
	$('#templateModel .modal-title').html('创建新模板');
	$('#templateModel').modal('show');
}

function editTemplate(data) {
	$('#templateId').val(data.templateId);
	$('#templateName').val(data.templateName);
	$('#templateType').val(data.templateType);

	$('#templateModel .modal-title').html('编辑模板');
	$('#templateModel').modal('show');
}

function removeTemplate(templateId) {
	bootbox.confirm("确定要删除选中模板么？", function(ret) {
		if (ret == true) {
			$.ajax({
				url : "./template/" + templateId,
				type : 'DELETE',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						$("#t_" + templateId).remove();
						if (!$('#deptpanel_2').hasClass('hide'))
							$('#deptpanel_2').addClass('hide');
						if (!$('#deptpanel_3').hasClass('hide'))
							$('#deptpanel_3').addClass('hide');
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

function selectTemplate(templateId) {
	var item = $('#t_' + templateId);
	// 首先移除其他active节点标示
	item.parent().children(".active").each(function(index, element) {
		$(element).removeClass("active");
	});

	item.addClass("active");

	$("#deptpanel_2").removeClass('hide');
	$("#deptpanel_3").removeClass('hide').addClass('hide');

	listTemplateDepts(templateId);
}

function listTemplateDepts(templateId) {
	$.ajax({
		url : "./template/depts/" + templateId,
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				// set template list
				$("#templateDeptListGroup").html('');
				for (var i = 0; i < resp.data.length; i++) {
					addTemplateDeptItem(resp.data[i]);
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

function addTemplateDeptItem(data) {
	$("#templateDeptListGroup").append(
			'<a id="c_' + data.configId + '" dept="' + data.deptId
					+ '" onclick="selectTemplateDept(' + data.configId
					+ ')" class="dept-item list-group-item"><span>'
					+ data.deptName + '</span><span></span></a>');
	var item = $("#c_" + data.configId);
	var span = item.children("span:last");

	item.hover(function() {
		span.html('<i class="fa fa-trash-o fa-fw" title="删除"></i>');

		// handler
		span.find(".fa-trash-o:first").click(function(e) {
			removeTemplateDept(data.configId);
			return false;
		});
	}, function() {
		span.html('');
	});
}

var bDeptInit = false;
function addTemplateDept() {
	if (bDeptInit == false) {
		bDeptInit = true;
		// load dept list at the 1st time
		$('#targetDepts').cxSelect({
			url : './depts/select',
			selects : [ 'dept_1', 'dept_2', 'dept_3' ],
			required : false,
			nodata : 'none',
		});
	}

	$("#alldeptsChx").prop("checked", false);
	$('#templateDeptModel').modal('show');
}

function removeTemplateDept(configId) {
	bootbox.confirm("确定要删除选中配置部门么？", function(ret) {
		if (ret == true) {
			$.ajax({
				url : "./template/dept/" + configId,
				type : 'DELETE',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						$("#c_" + configId).remove();
						if (!$('#deptpanel_3').hasClass('hide'))
							$('#deptpanel_3').addClass('hide');
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

function selectTemplateDept(configId) {
	var item = $('#c_' + configId);
	// 首先移除其他active节点标示
	item.parent().children(".active").each(function(index, element) {
		$(element).removeClass("active");
	});

	item.addClass("active");

	var subPanel = $("#deptpanel_3");
	subPanel.removeClass('hide');
	listTemplateDeptApprovals(configId);
}

function listTemplateDeptApprovals(configId) {
	$.ajax({
		url : "./template/dept/approvals/" + configId,
		type : 'GET',
		beforeSend : function() {
			// _templateDeptApprovals = null;
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				// set template list
				$("#templateDeptApprovalListGroup").html('');
				// _templateDeptApprovals = resp.data;
				for (var i = 0; i < resp.data.length; i++) {
					addTemplateDeptApprovalItem(resp.data[i]);
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

function addTemplateDeptApprovalItem(data) {
	$("#templateDeptApprovalListGroup")
			.append(
					'<a id="u_'
							+ data.userId
							+ '" onclick="return false;" class="dept-item list-group-item"><span>'
							+ data.userName + '</span><span></span></a>');
	var item = $("#u_" + data.userId);
	var span = item.children("span:last");

	item.hover(function() {
		span.html('<i class="fa fa-trash-o fa-fw" title＝"删除"></i>');

		// handler
		span.find(".fa-trash-o:first").click(function(e) {
			removeTemplateDeptApproval(data.configId, data.userId);
			return false;
		});
	}, function() {
		span.html('');
	});
}

function removeTemplateDeptApproval(configId, userId) {
	bootbox.confirm("确定要删除选中审批人么？", function(ret) {
		if (ret == true) {
			$.ajax({
				url : "./template/dept/approval/" + configId + "/" + userId,
				type : 'DELETE',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						$("#u_" + userId).remove();
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

var bApprovalDialogInit = false;
function addTemplateDeptApproval() {
	if (bApprovalDialogInit == false) {
		bApprovalDialogInit = true;
		// load dept list at the 1st time
		$('#approvalDepts').cxSelect({
			url : './depts/select',
			selects : [ 'dept_1', 'dept_2', 'dept_3' ],
			required : false,
			nodata : 'none',
		});

		$('#approvalDepts dept_1').change(function() {
			var val = Number($(this).val());
			if (val > 0) {
				queryMembers(val, 1);
			} else {
				queryMembers(0, 1);
			}
		});

		$('#approvalDepts .dept_2').change(function() {
			var val = Number($(this).val());
			if (val <= 0) {
				val = Number($('#approvalDepts .dept_1').val());
			}

			queryMembers(val, 1);
		});

		$('#approvalDepts .dept_3').change(function() {
			var val = Number($(this).val());
			if (val <= 0) {
				val = Number($('#approvalDepts .dept_2').val());
			}

			queryMembers(val, 1);
		});
	}

	// clear data
	$("#mytable input[type='checkbox']").each(function() {
		$(this).prop('checked', false);
	});
	$('#selectedMembers').tagsinput('removeAll');

	// init existing approvals
	/*
	 * if (_templateDeptApprovals != null) { for (var i = 0; i <
	 * _templateDeptApprovals.length; i++) { var data =
	 * _templateDeptApprovals[i]; $('#selectedMembers').tagsinput('add', { value :
	 * data.userId, text : data.userName }); } }
	 */

	$('#templateDeptApprovalModel').modal('show');
}

function queryMembers(deptId, page) {
	if (deptId <= 0) {
		$("#mytable tbody").html(
				'<tr align="center"><td colspan="7">请选择部门</td></tr>');
		return false;
	}

	// 根据部门查询成员列表
	$.ajax({
		url : "./members/" + deptId + "/" + page,
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				setMemberList(resp.data);
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

function setMemberList(pagedata) {
	var html = '';

	if (pagedata.data.length > 0) {
		for (var i = 0; i < pagedata.data.length; i++) {
			var row = pagedata.data[i];
			html += '<tr>';
			html += '<td><input id=' + row.userId
					+ ' name="chk_list" type="checkbox"/></td>';
			html += '<td>' + row.userName + '</td>';
			html += '<td>' + row.companyName + '</td>';
			html += '<td>' + row.position + '</td>';
			html += '<td>' + MEMBER_STATUS_MAP[row.status] + '</td>';
			html += '</tr>';
		}
	} else {
		html += '<tr align="center"><td colspan="5">该部门暂无成员数据。</td></tr>';
	}

	$("#mytable tbody").html(html);
	$("#checkall").prop("checked", false);

	$("input[name='chk_list']").each(function() {
		$(this).change(function() {
			var userId = $(this).attr('id');
			var userName = $(this).parent().next().html();

			if ($(this).prop("checked")) {
				// check if its already exist
				if ($("#u_" + userId).length > 0) {
					bootbox.alert('该审批人已添加');
					$(this).prop("checked", false);
					return false;
				}

				$('#selectedMembers').tagsinput('add', {
					value : userId,
					text : userName
				});
			} else {
				$('#selectedMembers').tagsinput('remove', {
					value : userId,
					text : userName
				});
			}

		});
	});

	// set page bar
	setPageUI(pagedata.page, "pagebar", function(page) {
		queryMembers(getSelectedDept(false), page);
	});
}

function getSelectedTemplate() {
	var activeItem = $("#templateListGroup .active:first");
	if (activeItem.length == 0) {
		bootbox.alert('没有选择模板');
		return null;
	}
	return Number(activeItem.attr('id').substr(2));
}

function getSelectedDeptConfig() {
	var activeItem = $("#templateDeptListGroup .active:first");
	if (activeItem.length == 0) {
		bootbox.alert('没有选择模板');
		return null;
	}
	return Number(activeItem.attr('id').substr(2));
}

// bDeptPick： true标示是选择的适配部门ID，false是返回查询审批人的部门ID
function getSelectedDept(bDeptPick) {
	if (bDeptPick && $("#alldeptsChx").prop("checked"))
		return -1;

	var deptContainer = bDeptPick ? 'targetDepts' : 'approvalDepts';

	var val = $('#' + deptContainer + ' .dept_3').val();
	if (val != null && Number(val) != 0)
		return Number(val);

	var val = $('#' + deptContainer + ' .dept_2').val();
	if (val != null && Number(val) != 0)
		return Number(val);

	var val = $('#' + deptContainer + ' .dept_1').val();
	if (val != null && Number(val) != 0)
		return Number(val);

	return null;
}
