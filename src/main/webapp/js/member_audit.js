/**
 * 审核成员个人信息
 */
$(function() {
	$('#backlink').click(function() {
		history.go(-1);
	});

	queryApplyList(0);
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
			html += '<tr onclick="viewChange(' + row.userId + ')">';
			html += '<td>' + row.userName + '</td>';
			html += '<td>' + row.applyDate + '</td>';
			html += '</tr>';
		}
	} else {
		html = '<tr align="center"><td colspan="2">暂无待审核信息</td></tr>';
	}

	$('#applyListTable tbody').html(html);

}

function viewChange(userId) {
	
}