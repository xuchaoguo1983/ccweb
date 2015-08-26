/**
 * 外勤记录
 */

$(function() {

	$('#selectedMembers').tagsinput({
		itemValue : 'value',
		itemText : 'text',
	});

	$('#userSelBtn').click(function() {
		selectCheckoutMembers();
	});

	$('#setSelectUserBtn').click(function() {
		$('#selectedMembers').tagsinput('removeAll');

		$("input[name='chk_list']").each(function() {
			if ($(this).prop("checked")) {
				var userId = $(this).attr('id');
				var userName = $(this).parent().next().html();

				$('#selectedMembers').tagsinput('add', {
					value : userId,
					text : userName
				});
			}
		});

		$('#memberSelectModal').modal('hide');

		queryCheckouts(1);
	});

	$('#selectedMembers').on('itemRemoved', function(event) {
		queryCheckouts(1);
	});

	queryCheckouts(1);
});

function queryCheckouts(page) {
	$.ajax({
		url : "./checkout/list/" + page,
		type : 'POST',
		data : {
			"userIds" : $('#selectedMembers').val(),
		},
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				setCheckoutList(resp.data);
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

function setCheckoutList(pagedata) {
	var html = '';

	if (pagedata.data.length > 0) {
		for (var i = 0; i < pagedata.data.length; i++) {
			var row = pagedata.data[i];

			html += '<a id="c_' + row.checkoutId + '" userid="' + row.userId
					+ '" username="' + row.userName
					+ '" onclick="viewCheckout(' + row.checkoutId
					+ ')" class="list-group-item">';
			html += '<img class="img-circle" src="' + row.userPhoto + '" />';
			html += '<span class="outClient">客户名称：' + row.clientName
					+ '</span>';
			html += '<span class="operation"></span>';
			html += '<span class="outPlace">外勤地点：' + row.outPlace + '</span> ';
			html += '<span class="outTime">外勤时间：' + row.outTime + '</span>';
			html += '<span class="outUser">外勤人：' + row.userName + '</span>';

			html += '</a>';			
		}
	} else {
		html += '<a class="list-group-item text-center">查询结果为空。</a>';
	}

	$("#mylistview_2").html(html);

	$("#mylistview_2 .list-group-item").each(function() {
		var id = $(this).attr('id');
		if (id == undefined)
			return false;

		var span = $(this).children('.operation:first');
		$(this).hover(function() {
			span.html('<i class="fa fa-binoculars fa-fw" title="筛选:'+$(this).attr('username')+'"></i>');
			span.children(".fa-binoculars:first").click(function(e) {				
				$('#selectedMembers').tagsinput('removeAll');
				$('#selectedMembers').tagsinput('add', {
					value : $(this).parent().parent().attr('userid'),
					text : $(this).parent().parent().attr('username')
				});
				queryCheckouts(1);
				return false;
			});
		}, function() {
			span.html('');
		});

		$(this).click(function() {
			location.href = "./checkout/view/" + id.substr(2);
		});
	});

	// set page bar
	setPageUI(pagedata.page, "pagebar", function(page) {
		queryCheckouts(page);
	});
}

var checkOut_DeptInited = false;
function selectCheckoutMembers() {
	if (checkOut_DeptInited == false) {
		checkOut_DeptInited = true;
		// load dept list at the 1st time
		$('#seldepts').cxSelect({
			url : './depts/select',
			selects : [ 'dept_1', 'dept_2', 'dept_3' ],
			required : false,
			nodata : 'none',
		});

		$('#seldepts dept_1').change(function() {
			var val = Number($(this).val());
			if (val > 0) {
				queryMembers(val, 1);
			} else {
				queryMembers(0, 1);
			}
		});

		$('#seldepts .dept_2').change(function() {
			var val = Number($(this).val());
			if (val <= 0) {
				val = Number($('#seldepts .dept_1').val());
			}

			queryMembers(val, 1);
		});

		$('#seldepts .dept_3').change(function() {
			var val = Number($(this).val());
			if (val <= 0) {
				val = Number($('#seldepts .dept_2').val());
			}

			queryMembers(val, 1);
		});
	}

	// clear data
	$("#mytable input[type='checkbox']").each(function() {
		$(this).prop('checked', false);
	});

	$('#memberSelectModal').modal('show');
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

	// set page bar
	setPageUI(pagedata.page, "pagebar2", function(page) {
		queryMembers(getSelectedDept(), page);
	});
}

function getSelectedDept() {
	var val = $('#seldepts .dept_3').val();
	if (val != null && Number(val) != 0)
		return Number(val);

	var val = $('#seldepts .dept_2').val();
	if (val != null && Number(val) != 0)
		return Number(val);

	var val = $('#seldepts .dept_1').val();
	if (val != null && Number(val) != 0)
		return Number(val);

	return null;
}

function viewCheckout(checkoutId) {
	location.href = "./checkout/" + checkoutId;
}
