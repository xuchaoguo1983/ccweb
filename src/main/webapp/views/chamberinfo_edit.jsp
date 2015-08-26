<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商会管理</title>
<link rel="stylesheet" href="./css/summernote.css">

</head>
<body>
	<input type="hidden" id="category" name="category" value="${category}" />

	<div id="titlebar">
		<a id="backlink"><i class="fa fa-chevron-left fa-fw"></i>返回</a> <span
			class="title2">新建商会新闻</span>
	</div>

	<div id="formdiv">
		<form id="chamberinfoform" class="form-horizontal" role="form"
			data-toggle="validator">
			<input type="hidden" id="type" name="type" value="${type}" /> <input
				type="hidden" id="infoId" name="infoId"
				value="${chamberInfo.infoId }" />

			<div class="form-group">
				<label for="title" class="col-sm-2 control-label">标题：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="title"
						value="${chamberInfo.title }" data-error="请输入标题" required>
					<div class="help-block with-errors"></div>
				</div>
			</div>

			<div id="contentdiv" class="form-group">
				<label for="content" class="col-sm-2 control-label">内容：</label>
				<div class="col-sm-9">
					<div id="content"></div>
					<div class="help-block with-errors"></div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label> <input id="isHot" name="isHot" type="checkbox"
							value="1"> 是否热点
						</label>
					</div>
				</div>
			</div>

			<div id="hotImageDiv" class="form-group" style="display: none;">
				<label for="hotImage" class="col-sm-2 control-label">热点图片：</label>
				<div class="col-sm-9">
					<input type="file" id="file" name="file" />
				</div>
				<div class="col-sm-offset-2 col-sm-9 ">
					<div class="help-block with-errors"></div>

					<div id="progress" class="progress" style="display: none;">
						<div class="progress-bar progress-bar-success"></div>
					</div>
				</div>

				<div class="col-sm-offset-2 col-sm-6 col-md-3">
					<div class="thumbnail">
						<img id="hotImage" src="${chamberInfo.hotImage}" alt="预览">
					</div>
				</div>
			</div>

		</form>
	</div>

	<div id="footbtns">
		<button id="saveBtn" type="button" class="btn btn-primary">确
			定</button>
		<button id="cancelBtn" type="button" class="btn btn-default">取
			消</button>
	</div>

	<script src="./js/jquery.ui.widget.js"></script>
	<script src="./js/jquery.iframe-transport.js"></script>
	<script src="./js/jquery.fileupload.js"></script>
	<script src="./js/summernote.min.js"></script>
	<script src="./js/summernote-zh-CN.js"></script>

	<script src="./js/chamberinfo_edit.js"></script>

	<c:if test="${not empty chamberInfo.content }">
		<script type="text/javascript">
			$(function() {
				$('#content').code('${chamberInfo.content}');
			});
		</script>
	</c:if>

	<c:if test="${chamberInfo.isHot==1}">
		<script type="text/javascript">
			$(function() {
				$('#isHot').click();
			});
		</script>
	</c:if>
</body>
</html>