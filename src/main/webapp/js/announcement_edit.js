/**
 * 添加或编辑公告
 */

$(function() {
	$('#backlink').click(function() {
		history.go(-1);
	});

	$('#cancelBtn').click(function() {
		history.go(-1);
	});

	$('#content').summernote({
		lang : 'zh-CN',
		height : 300,
		minHeight : 100,
		maxHeight : 600,
	}).on('summernote.change', function(customEvent, contents, $editable) {
		// 检查内容
		var isEmpty = $('#content').summernote('isEmpty');
		if (isEmpty) {
			setErrorMessage('contentdiv', '公告内容不能为空');
		} else {
			setErrorMessage('contentdiv');
		}
	});

	$('#target').change(function() {
		$('#selectedTargets').tagsinput('removeAll');
	});

	// 选择对象
	$('#selTargetBtn').click(function() {
		if (Number($('#target').val()) == 2) {
			// 部门
			loadDepts();
		} else {
			// 职务
			loadJobs();
		}

		$('#targetModel').modal('show');
	});
	// 保存选择对象
	$('#setTargetsBtn').click(function() {
		var selectedNodes = $('#targetTreediv').jstree('get_selected', true);
		if (selectedNodes.length == 0) {
			bootbox.alert('请选择通知对象');
			return false;
		}

		$('#selectedTargets').tagsinput('removeAll');
		for (var i = 0; i < selectedNodes.length; i++) {
			var node = selectedNodes[i];
			if (Number(node.id) < 0)
				continue;

			$('#selectedTargets').tagsinput('add', {
				id : node.id,
				text : node.text
			// $('#targetTreediv').jstree('get_path', node, '/', false)
			});
		}

		$('#targetModel').modal('hide');
	});

	$('#saveBtn').click(function() {
		$("#status").val("1");
		$('#annnoucementform').submit();
	});
	
	$('#saveDraftBtn').click(function() {
		$("#status").val("9");
		$('#annnoucementform').submit();
	});

	// 弹出窗口提交校验
	$('#annnoucementform').validator().on('submit', function(e) {
		if (e.isDefaultPrevented()) {
			// validate failed
		} else {
			// 检查内容
			var isEmpty = $('#content').summernote('isEmpty');
			if (isEmpty) {
				setErrorMessage('contentdiv', '公告内容不能为空');
				return false;
			} else {
				setErrorMessage('contentdiv');
			}

			// 提交数据
			$.ajax({
				url : "./announcement/save",
				type : 'POST',
				data : {
					"announcementId" : $('#announcementId').val(),
					"title" : $('#title').val(),
					"content" : $('#content').code(),
					"target" : $('#target').val(),
					"targetIds" : $('#selectedTargets').val(),
					"status" : $('#status').val()
				},
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						location.href = "./announcement";
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

	if ($('#announcementId').val() == '') {
		$('#titlebar .title2').html('新建公告');
	} else {
		$('#titlebar .title2').html('编辑公告');
	}
});

function loadDepts() {
	$.ajax({
		url : "./jsTree/depts",
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			initTreeData(resp);
		},
		error : function(data, status, er) {
			loadUI.hidePleaseWait();
			bootbox.alert("数据请求出错：" + er);
		}
	});
}

function loadJobs() {
	$.ajax({
		url : "./jsTree/jobs",
		type : 'GET',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			initTreeData(resp);
		},
		error : function(data, status, er) {
			loadUI.hidePleaseWait();
			bootbox.alert("数据请求出错：" + er);
		}
	});
}

function initTreeData(data) {
	$('#targetTreediv').jstree("destroy");

	$('#targetTreediv').jstree({
		'core' : {
			'data' : data
		},
		"plugins" : [ "checkbox" ],
		"checkbox" : {
			"keep_selected_style" : false
		},
	});

	checkSelectedTargets();
}

function checkSelectedTargets() {
	// do it after loading
	$('#targetTreediv').bind(
			'loaded.jstree',
			function(e, data) {
				var selectedTargetIds = $('#selectedTargets').val().split(",");
				for (var i = 0; i < selectedTargetIds.length; i++) {
					$("#targetTreediv").jstree("check_node",
							"#" + selectedTargetIds[i]);
				}
			});
}
