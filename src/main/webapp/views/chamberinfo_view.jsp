<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商会管理</title>
</head>
<body>
	<div id="titlebar">
		<a id="backlink"><i class="fa fa-chevron-left fa-fw"></i>返回</a> <span
			class="title2">信息详情</span>
	</div>

	<div id="previewDiv">
		<p class="title">${chamberInfo.title }
			<c:if test="${chamberInfo.isHot == 1}">
				<i class="fa fa-star fw" title="热点"></i>
			</c:if>
		</p>
		<p class="subtitle">
			<span>发布人：${chamberInfo.publisherName}</span><span>发布时间：${chamberInfo.publishTime }</span>
			<span id="categoryName"></span>
		</p>

		<div id="contentdiv">${chamberInfo.content }</div>
		<hr />
		<p class="foot">
			<span>已阅读：${chamberInfo.readTimes }次</span>
		</p>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#backlink').click(function() {
				history.back();
			});
			
			var categoryName = CHAMBER_INFO_TYPE['${category}'];
			var typeName = getChamberTypeName('${type}');
			$('title').html(categoryName);
			$('#categoryName').html('类别：' + typeName);
			$('#titlebar .title2').html(typeName + "详情");

		});
	</script>
</body>
</html>