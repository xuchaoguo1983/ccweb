<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商会管理</title>
</head>
<body>
	<input type="hidden" id="category" name="category" value="${category}" />
	<input type="hidden" id="type" name="type" value="${type}" />

	<div id="titlebar">
		<span class="title">商会管理</span>
		<div class="btn-group" data-toggle="buttons"></div>
		<span class="operation"> <a id="addChamberInfoLink"> <i
				class="fa fa-plus fa-fw"></i>新建
		</a>
		</span>
	</div>

	<div class="container-fluid">
		<div id="mylistview_1" class="list-group"></div>

		<!-- page bar -->
		<nav id="pagebar" class="pagebar" style="display: none;">
			<ul class="pagination">

			</ul>
		</nav>
	</div>


	<script src="./js/page.js"></script>
	<script src="./js/chamberinfo.js"></script>
</body>
</html>