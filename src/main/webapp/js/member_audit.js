/**
 * 审核成员个人信息
 */
$(function() {
	$('#backlink').click(function() {
		history.go(-1);
	});

	initSelectOptions('verifyStatus', MEMBER_VERIFY_STATUS);

	queryApplyList(1);

	$('#verifyBtn').click(function() {
		var activeRecord = $('#applyListTable tbody tr.active');
		if (activeRecord.length != 1) {
			bootbox.alert('请选择要审核的修改记录');
			return false;
		}

		$('#verifyform')[0].reset();

		var recordId = activeRecord.data('id');
		var userId = activeRecord.data('userid');
		var companyId = activeRecord.data('companyid');

		$('#verifyform #userId').val(userId);
		$('#verifyform #recordId').val(recordId);
		$('#verifyform #companyId').val(companyId);

		$('#verifyModel').modal('show');
	});

	$('#verifyModel #saveBtn').click(function() {
		$.ajax({
			url : "./member/audits/verify",
			type : 'POST',
			data : $('#verifyform').serialize(),
			beforeSend : function() {
				loadUI.showPleaseWait();
			},
			success : function(resp) {
				loadUI.hidePleaseWait();
				if (resp.code == 0) {
					$('#verifyModel').modal('hide');
					queryApplyList(1);
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
});

function queryApplyList(page) {
	$.ajax({
		url : "./member/audits/" + page,
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				setApplyList(resp.data);
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

function setApplyList(pagedata) {
	var html = '';
	if (pagedata.data.length > 0) {
		for (var i = 0; i < pagedata.data.length; i++) {
			var row = pagedata.data[i];
			html += '<tr data-id="' + row.recordId + '" data-userid="'
					+ row.userId + '" data-companyid="' + row.companyId + '">';
			html += '<td>' + row.userName + '</td>';
			html += '<td>' + row.applyDate + '</td>';
			html += '</tr>';
		}
	} else {
		html = '<tr align="center"><td colspan="2">暂无待审核信息</td></tr>';
	}

	$('#applyListTable tbody').html(html);
	
	clearInfoChange();

	if (pagedata.data.length > 0) {
		$('#applyListTable tbody tr').click(function() {
			var that = this;
			var recordId = $(this).data('id');
			var userId = $(this).data('userid');
			var companyId = $(this).data('companyid');

			$.ajax({
				url : "./member/audits/view/" + userId,
				data : 'recordId=' + recordId + "&companyId=" + companyId,
				type : 'GET',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						var lastActive = $('#applyListTable tbody tr.active');
						if (lastActive.length > 0) {
							lastActive.removeClass('active');
						}

						$(that).addClass('active');

						showInfoChange(resp.data);
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

	}
}

function clearInfoChange() {
	var changeInfoDiv = $('#infochangediv');

	for (var i = 0; i < 2; i++) {
		$("#userNameCell" + i, changeInfoDiv).html('');
		$("#mobileCell" + i, changeInfoDiv).html('');
		$("#officeTellCell" + i, changeInfoDiv).html('');
		$("#emailCell" + i, changeInfoDiv).html('');
		$("#birthPlaceCell" + i, changeInfoDiv).html('');
		$("#companyNameCell" + i, changeInfoDiv).html('');
		$("#positionCell" + i, changeInfoDiv).html('');
		$("#addressCell" + i, changeInfoDiv).html('');
	}
}

function showInfoChange(changedata) {
	clearInfoChange();
	if (changedata.length != 2) {
		bootbox.alert('数据错误');
		return false;
	}

	var changeInfoDiv = $('#infochangediv');
	for (var i = 0; i < 2; i++) {
		var data = changedata[i];
		if (data == null) {
			bootbox.alert('数据错误');
			return;
		}

		$("#userNameCell" + i, changeInfoDiv).html(data.userName || '');
		$("#mobileCell" + i, changeInfoDiv).html(data.mobile || '');
		$("#officeTellCell" + i, changeInfoDiv).html(data.officeTell || '');
		$("#emailCell" + i, changeInfoDiv).html(data.email || '');
		$("#birthPlaceCell" + i, changeInfoDiv).html(data.birthPlace || '');
		$("#companyNameCell" + i, changeInfoDiv).html(data.companyName || '');
		$("#positionCell" + i, changeInfoDiv).html(data.position || '');
		$("#addressCell" + i, changeInfoDiv).html(data.address || '');
	}

	// 高亮变动项
	$.each(changedata[1], function(k, v) {
		var div = $("#" + k + "Cell1", changeInfoDiv);
		if (div.length == 0) {
			return;
		}

		if (changedata[0][k] != v) {
			div.css('color', 'red');
		} else {
			div.css('color', '');
		}
	});
}
