/**
 * 成员管理模块
 */

$(function() {
	$('#depts').cxSelect({
		url : './depts/select',
		selects : [ 'dept_1', 'dept_2', 'dept_3', 'dept_4', 'dept_5' ],
		required : false,
		nodata : 'none',
	});

	$('#depts .dept_1').change(function() {
		var val = Number($(this).val());
		if (val > 0) {
			queryMembers(val, 1);
		} else {
			queryMembers(0, 1);
		}
	});

	$('#depts .dept_2').change(function() {
		var val = Number($(this).val());
		if (val <= 0) {
			val = Number($('#depts .dept_1').val());
		}

		queryMembers(val, 1);
	});

	$('#depts .dept_3').change(function() {
		var val = Number($(this).val());
		if (val <= 0) {
			val = Number($('#depts .dept_2').val());
		}

		queryMembers(val, 1);
	});

	$('#depts .dept_4').change(function() {
		var val = Number($(this).val());
		if (val <= 0) {
			val = Number($('#depts .dept_3').val());
		}

		queryMembers(val, 1);
	});

	$('#depts .dept_5').change(function() {
		var val = Number($(this).val());
		if (val <= 0) {
			val = Number($('#depts .dept_4').val());
		}

		queryMembers(val, 1);
	});
	// init select
	initSelectOptions('status', MEMBER_STATUS_MAP);

	$('#addMemberLink').click(function() {
		$('#memberModel .modal-title').html('添加新成员');

		var deptId = getActiveDept();
		if (deptId == null || deptId == 0) {
			bootbox.alert('请先选择部门');
			return false;
		}
		// clear the form
		$('#memberform')[0].reset();
		$("#memberform #userId").val('');
		$('#memberModel').modal('show');
	});

	$('#removeMemberLink').click(function() {
		var idArray = getCheckedList();
		if (idArray.length == 0) {
			bootbox.alert('请勾选要删除的成员，可多选');
			return false;
		}

		bootbox.confirm("确定要删除选中成员么？", function(ret) {
			if (ret == true) {
				$.ajax({
					url : "./member/" + getActiveDept() + "/" + idArray.join(),
					type : 'DELETE',
					beforeSend : function() {
						loadUI.showPleaseWait();
					},
					success : function(resp) {
						loadUI.hidePleaseWait();
						if (resp.code == 0) {
							setMemberList(resp.data);
							closeInfoBar();
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
	});

	var deptLoaded = false;
	$("#moveMemberLink").click(function() {
		var idArray = getCheckedList();
		if (idArray.length == 0) {
			bootbox.alert('请勾选要移动的成员，可多选');
			return false;
		}

		if (deptLoaded == false) {
			deptLoaded = true;
			// load dept list at the 1st time
			$('#targetDepts').cxSelect({
				url : './depts/select',
				selects : [ 'dept_1', 'dept_2', 'dept_3', 'dept_4', 'dept_5' ],
				required : false,
				nodata : 'none',
			});
		}

		$("#deptMoveModel").modal('show');
	});

	$("#importMemberLink").click(function() {
		// reset form data
		$("#memberimportform")[0].reset();
		$("#memberImportBtn").text('上传');
		$('#progress .progress-bar').css('width', '0%');
		$("#memberImportModel").modal('show');
	});

	// 弹出窗口提交校验
	$('#memberform').validator().on('submit', function(e) {
		if (e.isDefaultPrevented()) {
			// validate failed
		} else {
			$.ajax({
				url : "./member/" + getActiveDept(),
				data : $('#memberform').serialize(),
				type : 'POST',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						$('#memberModel').modal('hide');
						setMemberList(resp.data);
						closeInfoBar();
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

	$('#saveBtn').click(function() {
		$('#memberform').submit();
	});

	$('#moveDeptBtn').click(function() {
		var targetDeptId = getActiveDept('targetDepts');
		if (targetDeptId == null) {
			bootbox.alert('请选择移动的目标部门');
			return false;
		}

		var sourceDeptId = getActiveDept();
		if (targetDeptId == sourceDeptId) {
			bootbox.alert('目标部门与原部门相同，请重新选中');
			return false;
		}

		$.ajax({
			url : "./member/" + sourceDeptId + "/to/" + targetDeptId,
			data : {
				"userIds" : getCheckedList().join()
			},
			type : 'POST',
			beforeSend : function() {
				loadUI.showPleaseWait();
			},
			success : function(resp) {
				loadUI.hidePleaseWait();
				if (resp.code == 0) {
					$('#deptMoveModel').modal('hide');
					setMemberList(resp.data);
					closeInfoBar();
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

	$('#file').fileupload({
		url : "./member/upload",
		replaceFileInput : false,
		add : function(e, data) {
			$("#memberImportBtn").text('上传');
			$("#memberImportBtn").click(function() {
				if (!(/(\.|\/)(xls|xlsx)$/i).test(data.files[0].name)) {
					bootbox.alert('不能识别的文件格式');
					return;
				}

				data.context = $(this).text('正在上传...');
				data.submit();
				loadUI.showPleaseWait();
			});
		},
		progressall : function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			$('#progress .progress-bar').css('width', progress + '%');
		},
		done : function(e, data) {
			loadUI.hidePleaseWait();
			var ret = data.result;
			if (ret.code == 0) {
				data.context.text('上传成功');
				// since depts might be updated, refresh the page
				location.reload();
			} else {
				bootbox.alert(ret.message);
			}
		},
		fail : function(e, data) {
			loadUI.hidePleaseWait();
			data.context.text('上传失败');
			bootbox.alert('文件上传失败：' + data.errorThrown);
		}
	});
});

function getActiveDept(deptdiv) {
	if (deptdiv == null)
		deptdiv = 'depts';

	for (var i = 5; i > 0; i--) {
		var val = $('#' + deptdiv + ' .dept_' + i).val();
		if (val != null && Number(val) != 0)
			return Number(val);
	}

	return null;
}

function getActiveDeptName() {
	var name = '';

	for (var i = 1; i <= 5; i++) {
		var dept = $('#depts .dept_' + i);
		var val = dept.val();
		if (val != null && Number(val) != 0) {
			if (name.length > 0)
				name += "/";
			name += dept.find("option:selected").text();
		}
	}

	return name;
}

function queryMembers(deptId, page) {
	$("#infobar").hide();

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
			html += '<tr onclick="viewMember(' + row.userId + ')">';
			html += '<td><input id=' + row.userId
					+ ' name="chk_list" type="checkbox"/></td>';
			html += '<td>' + row.userName + '</td>';
			html += '<td>' + row.mobile + '</td>';
			html += '<td>' + row.companyName + '</td>';
			html += '<td>' + row.position + '</td>';
			html += '<td>' + MEMBER_STATUS_MAP[row.status] + '</td>';
			html += '<td>' + row.birthPlace + '</td>';
			html += '</tr>';
		}
	} else {
		html += '<tr align="center"><td colspan="7">该部门暂无成员数据。</td></tr>';
	}

	$("#mytable tbody").html(html);
	$("#checkall").prop("checked", false);
	// prevent checkbox click event propagation to open detail view
	$("#mytable tbody input[name='chk_list']").each(function() {
		$(this).click(function(e) {
			e.stopPropagation();
		});
	});

	// set page bar
	setPageUI(pagedata.page, "pagebar", function(page) {
		queryMembers(getActiveDept(), page);
	});
}

function viewMember(userid) {
	// 读取用户数据
	$.ajax({
		url : "./member/" + userid,
		data : {},
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				setMemberInfo(resp.data);
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

function setMemberInfo(data) {
	if (data == null) {
		// it should not happen
		bootbox.alert('获取成员信息失败');
		return false;
	}
	// init ui info
	$("#infobar #userImageCell").attr("src", data.userImage);
	$("#infobar #userNameCell").html(data.userName);
	$("#infobar #userDeptCell").html(getActiveDeptName());
	$("#infobar #mobileCell").html(data.mobile);
	$("#infobar #companyNameCell").html(data.companyName);
	$("#infobar #positionCell").html(data.position);
	$("#infobar #statusCell").html(MEMBER_STATUS_MAP[data.status]);
	$("#infobar #addressCell").html(data.address);
	$("#infobar #officeTellCell").html(data.officeTell);
	$("#infobar #birthPlaceCell").html(data.birthPlace);

	// bind click event
	$("#editMemberLink").unbind("click");
	$("#editMemberLink").bind(
			"click",
			function() {
				$("#memberform #userId").val(data.userId);
				$('#memberModel .modal-title').html('编辑成员');

				var deptId = getActiveDept();
				if (deptId == null || deptId == 0) {
					return false;
				}

				// copy member data
				$("#userName").val($("#infobar #userNameCell").html());
				$("#mobile").val($("#infobar #mobileCell").html());
				$("#companyName").val($("#infobar #companyNameCell").html());
				$("#position").val($("#infobar #positionCell").html());
				$("#status").val(
						getMapKeyByVal(MEMBER_STATUS_MAP, $(
								"#infobar #statusCell").html()));
				$("#address").val($("#infobar #addressCell").html());
				$("#officeTell").val($("#infobar #officeTellCell").html());
				$("#birthPlace").val($("#infobar #birthPlaceCell").html());

				$('#memberModel').modal('show');
			});

	$("#infobar").show();
}

function closeInfoBar() {
	$("#infobar").hide();
}
