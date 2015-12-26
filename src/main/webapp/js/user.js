/**
 * 当前用户信息编辑
 */

$(function() {
	// init select
	initSelectOptions('cur_status', MEMBER_STATUS_MAP);

	$("#editUserInfoLink").click(function() {
		// 编辑当前用户信息
		$.ajax({
			url : "./user/view",
			data : {},
			type : 'GET',
			beforeSend : function() {
				loadUI.showPleaseWait();
			},
			success : function(resp) {
				loadUI.hidePleaseWait();
				if (resp.code == 0) {
					setCurUserInfo(resp.data);
				} else {
					bootbox.alert(resp.message);
				}
			},
			error : function(data, status, er) {
				loadUI.hidePleaseWait();
				bootbox.alert("数据请求出错：" + er);
			}
		});
		$('#curUserModel').modal('show');
	});

	function setCurUserInfo(data) {
		$('#cur_userName').val(data.userName);
		$('#cur_mobile').val(data.mobile);
		$('#cur_officeTell').val(data.officeTell);
		$('#cur_email').val(data.email);
		$('#cur_companyName').val(data.companyName);
		$('#cur_address').val(data.address);
		$('#cur_position').val(data.position);
		$('#cur_status').val(data.status);
		$('#cur_birthPlace').val(data.birthPlace);
	}

	$('#saveCurUserBtn').click(function() {
		$('#curuserform').submit();
	});

	// 弹出窗口提交校验
	$('#curuserform').validator().on('submit', function(e) {
		if (e.isDefaultPrevented()) {
			// validate failed
		} else {
			$.ajax({
				url : "./user/save",
				data : $('#curuserform').serialize(),
				type : 'POST',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						$('#curUserModel').modal('hide');
						
						// reset user info
						var data = resp.data;
						$('#userinfo .adminName').html(data.userName);
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
});