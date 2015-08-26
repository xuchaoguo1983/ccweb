<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告编辑</title>
<link rel="stylesheet" href="./css/summernote.css">
<link rel="stylesheet" href="./css/bootstrap-tagsinput.css">
<link rel="stylesheet" href="./js/jstree/themes/default/style.min.css" />

</head>
<body>
	<div id="titlebar">
		<a id="backlink"><i class="fa fa-chevron-left fa-fw"></i>返回</a> <span
			class="title2">新建公告</span>
	</div>

	<div id="formdiv">
		<form id="annnoucementform" class="form-horizontal" role="form"
			data-toggle="validator">

			<input type="hidden" id="announcementId" name="announcementId"
				value="${announcement.announcementId }" />

			<div class="form-group">
				<label for="title" class="col-sm-2 control-label">公告标题：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="title"
						value="${announcement.title }" data-error="请输入公告标题" required>
					<div class="help-block with-errors"></div>
				</div>
			</div>

			<div id="contentdiv" class="form-group">
				<label for="content" class="col-sm-2 control-label">公告内容：</label>
				<div class="col-sm-9">
					<div id="content">${announcement.content }</div>
					<div class="help-block with-errors"></div>
				</div>
			</div>

			<div class="form-group">
				<label for="target" class="col-sm-2 control-label">通知类型：</label>
				<div class="col-sm-3">
					<select id="target" class="form-control"></select>
				</div>
			</div>

			<div id="targetdiv" class="form-group">
				<label for="target" class="col-sm-2 control-label">通知对象：</label>
				<div class="col-sm-9">
					<input id="selectedTargets" type="text" class="form-control"
						style="display: none;" />
					<div class="help-block with-errors"></div>
					<button id="selTargetBtn" class="btn btn-default" type="button">选择对象</button>
				</div>
			</div>
			<!--  
			<div class="form-group">
				<label for="title" class="col-sm-2 control-label">附件：</label>
				<div class="col-sm-9"></div>
			</div>
			-->
		</form>
	</div>

	<div id="footbtns">
		<button id="saveBtn" type="button" class="btn btn-primary">确
			定</button>
		<button id="cancelBtn" type="button" class="btn btn-default">取
			消</button>
	</div>

	<!-- select target dialog -->
	<div id="targetModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">选择通知对象</h4>
				</div>
				<div class="modal-body">
					<form id="targetselform" class="form-horizontal">
						<div class="form-group">
							<div class="panel panel-default col-sm-offset-1 col-sm-10">
								<div class="panel-body">
									<div id="targetTreediv" class="mytreeview"></div>
								</div>
							</div>

						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="setTargetsBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>


	<!-- 文件上传相关 -->
	<!--  
	<script src="./js/jquery.ui.widget.js"></script>
	<script src="./js/jquery.iframe-transport.js"></script>
	<script src="./js/jquery.fileupload.js"></script>
	-->
	<script src="./js/summernote.min.js"></script>
	<script src="./js/summernote-zh-CN.js"></script>
	<script src="./js/bootstrap-tagsinput.min.js"></script>
	<script src="./js/jstree/jstree.min.js"></script>

	<script src="./js/announcement_edit.js"></script>
	<script type="text/javascript">
		var targetIds = '${announcement.targetIds}';
		var targetNames = '${announcement.targetNames}';
		// init tag input 1st
		$('#selectedTargets').tagsinput({
			itemValue : 'id',
			itemText : 'text',
		});
		
		if (targetIds.length > 0) {
			var idArr = targetIds.split(",");
			var nameArr = targetNames.split(",");
			for (var i = 0; i < idArr.length; i++) {
				$('#selectedTargets').tagsinput('add', {
					id : idArr[i],
					text : nameArr[i]
				});
			}
		}

		initSelectOptions('target', ANNOUCEMENT_TARGET_TYPE);
		var selectedTarget = '${announcement.target}';
		if (selectedTarget.length > 0)
			$('#target').val(selectedTarget);
	</script>
</body>
</html>