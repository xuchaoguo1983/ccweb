/**
 * 商会信息编辑
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
			setErrorMessage('contentdiv', '内容不能为空');
		} else {
			setErrorMessage('contentdiv');
		}
	});

	var typeName = getChamberTypeName($('#type').val());
	if ($('#infoId').val() == '') {
		typeName = ('新建' + typeName);
	} else {
		typeName = ('编辑' + typeName);
	}

	$('title').html(CHAMBER_INFO_TYPE[$('#category').val()]);
	$('#titlebar .title2').html(typeName);

	$('#isHot').change(function() {
		if ($(this).prop("checked"))
			$('#hotImageDiv').show();
		else
			$('#hotImageDiv').hide();
	});

	$('#file').fileupload({
		url : baseUrl + "./fs/upload",
		replaceFileInput : false,
		add : function(e, data) {
			if (!(/\.(gif|jpg|jpeg|tiff|png)$/i).test(data.files[0].name)) {
				bootbox.alert('不能识别的文件格式');
				return;
			}

			$('#progress').show();
			$('#progress .progress-bar').css('width', '0%');
			data.submit();
			loadUI.showPleaseWait();
		},
		progressall : function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			$('#progress .progress-bar').css('width', progress + '%');
		},
		done : function(e, data) {
			loadUI.hidePleaseWait();
			var ret = data.result;
			if (ret.code == 0) {
				$('#hotImage').attr("src", ret.data);
				$('#progress').hide(); 
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

	$('#saveBtn').click(function() {
		$('#chamberinfoform').submit();
	});

	// 弹出窗口提交校验
	$('#chamberinfoform').validator().on(
			'submit',
			function(e) {
				if (e.isDefaultPrevented()) {
					// validate failed
				} else {
					// 检查内容
					var isEmpty = $('#content').summernote('isEmpty');
					if (isEmpty) {
						setErrorMessage('contentdiv', '内容不能为空');
						return false;
					} else {
						setErrorMessage('contentdiv');
					}

					if ($('#isHot').prop("checked")
							&& $('#hotImage').attr('src') == '') {
						setErrorMessage('hotImageDiv', '请上传热点图片');
						return false;
					} else {
						setErrorMessage('hotImageDiv');
					}

					// 提交数据
					$.ajax({
						url : baseUrl + "./chamberinfo/" + $('#category').val()
								+ "/save/" + $('#type').val(),
						type : 'POST',
						data : {
							"infoId" : $('#infoId').val(),
							"title" : $('#title').val(),
							"content" : $('#content').code(),
							"isHot" : $('#isHot').prop("checked") ? 1 : 0,
							"hotImage" : $('#hotImage').attr('src')
						},
						beforeSend : function() {
							loadUI.showPleaseWait();
						},
						success : function(resp) {
							loadUI.hidePleaseWait();
							if (resp.code == 0) {
								location.href = baseUrl + "./chamberinfo/"
										+ $('#category').val() + "?type="
										+ $('#type').val();
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
