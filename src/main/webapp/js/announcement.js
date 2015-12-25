/**
 * 公告管理模块
 */
var ann_startDate;
var ann_endDate;
$(function() {
	// init
	var date1 = new moment().subtract(1, 'months');
	var date2 = new moment();

	ann_startDate = (date1.format('YYYYMMDD'));
	ann_endDate = (date2.format('YYYYMMDD'));

	$('#dateRange').daterangepicker({
		autoApply : false,
		locale : {
			format : 'YYYY-MM-DD'
		},
		startDate: date1.format('YYYY-MM-DD'),
	    endDate: date2.format('YYYY-MM-DD')
	}, function(start, end, label) {
		ann_startDate = start.format('YYYYMMDD');
		ann_endDate = end.format('YYYYMMDD');

		queryAnnoucements(1);
	});

	$('input[name="statuBtn"]').each(function() {
		$(this).change(function() {
			queryAnnoucements(1);
		});
	});

	$('#addAnnouncementLink').click(function() {
		location.href = "./announcement/edit";
	});

	$('#verifyBtn').click(function() {
		$.ajax({
			url : "./announcement/verify",
			data : $('#verifyform').serialize(),
			type : 'POST',
			beforeSend : function() {
				loadUI.showPleaseWait();
			},
			success : function(resp) {
				loadUI.hidePleaseWait();
				if (resp.code == 0) {
					$('#verifyModel').modal('hide');
					$('.modal-backdrop').remove();
					queryAnnoucements(1);
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

	$('input[name="statuBtn"]').first().click();
	// queryAnnoucements(1);
});

function queryAnnoucements(page) {
	$.ajax({
		url : "./announcement/list/" + getStatus() + "/" + page,
		type : 'GET',
		data : {
			"beginDate" : ann_startDate,
			"endDate" : ann_endDate
		},
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				setAnnouncementList(resp.data);
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

function setAnnouncementList(pagedata) {
	var html = '';

	if (pagedata.data.length > 0) {
		for (var i = 0; i < pagedata.data.length; i++) {
			var row = pagedata.data[i];

			html += '<a id="a_' + row.announcementId
					+ '" onclick="viewAnnouncement(' + row.announcementId
					+ ')" class="list-group-item">';
			html += '<span class="title">' + row.title + '</span>';
			html += '<span class="operation"></span>';
			html += '<span class="publishTime">' + row.publishTime + '</span>';
			html += '<span class="publisher">发布人：' + row.publisherName
					+ '</span>';
			html += '</a>';
		}
	} else {
		html += '<a class="list-group-item text-center">查询结果为空。</a>';
	}

	$("#mylistview_1").html(html);

	var status = getStatus();
	$("#mylistview_1 .list-group-item").each(function() {
		$(this).click(function() {
			var id = $(this).attr('id');
			if (id != undefined) {
				location.href = "./announcement/view/" + id.substr(2);
			}
		});

		var span = $(this).children('.operation:first');
		$(this).hover(function() {
			span.html('');
			var id = $(this).attr('id');
			if (id == undefined)
				return false;

			if (status == 0) {
				span.append('<i class="fa fa-eye fa-fw" title="审核"></i>');
				span.children(".fa-eye:first").click(function(e) {
					verifyAnnouncement(id.substr(2));
					return false;
				});
			}

			if (status == 0 || status == 2 || status == 9) {
				span.append('<i class="fa fa-pencil fa-fw" title="编辑"></i>');
				span.children(".fa-pencil:first").click(function(e) {
					editAnnouncement(id.substr(2));
					return false;
				});
			}
			
			span.append('<i class="fa fa-trash-o fa-fw" title="删除"></i>');
			span.children(".fa-trash-o:first").click(function(e) {
				removeAnnouncement(id.substr(2));
				return false;
			});
		}, function() {
			span.html('');
		});
	});

	// set page bar
	setPageUI(pagedata.page, "pagebar", function(page) {
		queryAnnoucements(page);
	});
}

function editAnnouncement(announcementId) {
	location.href = "./announcement/edit/" + announcementId;
}

function verifyAnnouncement(id) {
	$('#verifyform')[0].reset();

	$('#verifyId').val(id);
	$('#verifyModel').modal('show');
}

function removeAnnouncement(id) {
	bootbox.confirm("确定要删除选中公告么？", function(ret) {
		if (ret == true) {
			$.ajax({
				url : "./announcement/delete/" + id,
				type : 'DELETE',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						$('#a_' + id).remove();
						$('.modal-backdrop').remove();
						queryAnnoucements(1);
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

function getStatus() {
	var status = 9;
	$('input[name="statuBtn"]').each(function() {
		if ($(this).prop('checked')) {
			status = $(this).val();
		}
	});

	return Number(status);
}