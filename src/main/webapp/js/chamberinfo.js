/**
 * 商会管理
 */

$(function() {
	var category = $('#category').val();
	var categoryName = CHAMBER_INFO_TYPE[category];

	$('title').html(categoryName);
	$('#titlebar .title').html(categoryName);

	var subTypes = CHAMBER_INFO_SUBTYPE[category];
	var subTypeGroup = $('#titlebar .btn-group');
	subTypeGroup.html('');
	for ( var key in subTypes) {
		subTypeGroup
				.append('<label class="btn"> <input type="radio" name="subTypeBtn" id="subTypeBtn" autocomplete="off" value="'
						+ key + '"> ' + subTypes[key] + '</label>');
	}

	subTypeGroup.find('input[type=radio]').each(function() {
		$(this).change(function() {
			$('#type').val($(this).val());
			queryChamberInfos(1);
		});
	});

	$('#addChamberInfoLink').click(function() {
		location.href = baseUrl + "./chamberinfo/" + $('#category').val() + "/edit/" + getActiveType();
	});

	var typeBtns = $('.btn-group input[type=radio]');
	if (typeBtns.length == 0) {
		// not sub type
		$('#type').val($('#category').val());
		queryChamberInfos(1);
	} else {
		var _selType = $('#type').val();
		if (Number(_selType) > 0) {
			typeBtns.each(function() {
				if ($(this).val() == _selType) {
					$(this).click();
				}
			});
		} else {
			typeBtns.first().click();
		}
	}
});

function queryChamberInfos(page) {
	$.ajax({
		url : baseUrl + "./chamberinfo/" + $('#category').val() + "/list/"
				+ getActiveType() + "/" + page,
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				setChamberInfoList(resp.data);
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

function setChamberInfoList(pagedata) {
	var html = '';

	if (pagedata.data.length > 0) {
		for (var i = 0; i < pagedata.data.length; i++) {
			var row = pagedata.data[i];

			html += '<a id="c_' + row.infoId + '" onclick="viewChamberInfo('
					+ row.infoId + ')" class="list-group-item">';

			if (Number(row.isHot) == 1) {
				html += '<span class="title">' + row.title
						+ '<i class="fa fa-star fw" title="热点"></i></span>';
			} else {
				html += '<span class="title">' + row.title + '</span>';
			}
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

	$("#mylistview_1 .list-group-item")
			.each(
					function() {
						var span = $(this).children('.operation:first');
						$(this)
								.hover(
										function() {
											span.html('');
											var id = $(this).attr('id');
											if (id == undefined)
												return false;

											span
													.html('<i class="fa fa-pencil fa-fw" title="编辑"></i><i class="fa fa-trash-o fa-fw" title="删除"></i>');
											span
													.children(
															".fa-pencil:first")
													.click(
															function(e) {
																editChamberInfo(id
																		.substr(2));
																return false;
															});
											span
													.children(
															".fa-trash-o:first")
													.click(
															function(e) {
																removeChamberInfo(id
																		.substr(2));
																return false;
															});
										}, function() {
											span.html('');
										});
					});

	// set page bar
	setPageUI(pagedata.page, "pagebar", function(page) {
		queryChamberInfos(page);
	});
}

function editChamberInfo(infoId) {
	location.href = baseUrl + "./chamberinfo/" + $("#category").val() + "/edit/"
			+ getActiveType() + "/" + infoId;
}

function removeChamberInfo(infoId) {
	bootbox.confirm("确定要删除选中" + getChamberTypeName(getActiveType()) + "么？",
			function(ret) {
				if (ret == true) {
					$.ajax({
						url : baseUrl + "./chamberinfo/" + $("category").val()
								+ "/delete/" + infoId,
						type : 'DELETE',
						beforeSend : function() {
							loadUI.showPleaseWait();
						},
						success : function(resp) {
							loadUI.hidePleaseWait();
							if (resp.code == 0) {
								$('#c_' + infoId).remove();
								$('.modal-backdrop').remove();
								queryChamberInfos(1);
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

function getActiveType() {
	return $('#type').val();
}

function viewChamberInfo(infoId) {
	location.href = baseUrl + "./chamberinfo/" + $("#category").val() + "/view/"
			+ getActiveType() + "/" + infoId;
}