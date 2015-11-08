<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>部门管理</title>
</head>
<body>
	<div id="titlebar">
		<span class="title">部门管理</span>
		<span class="operation"> <a
			id="dsgroup"> <i class="fa fa-group fa-fw"></i>管理讨论组
		</a></span>
	</div>

	<div style="padding-left:10px;padding-top: 15px;width:1300px;">
		<div id="deptpanel_1" class="mypanel">

			<!-- List group -->
			<div class="list-group"></div>

			<br /> <br />

			<div class="myfoot">
				<a onclick="createDept(1);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
			</div>
		</div>
		<div id="deptpanel_2" class="mypanel hide">

			<!-- List group -->
			<div class="list-group"></div>

			<br /> <br />

			<div class="myfoot">
				<a onclick="createDept(2);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
			</div>
		</div>
		<div id="deptpanel_3" class="mypanel hide">

			<!-- List group -->
			<div class="list-group"></div>

			<br /> <br />

			<div class="myfoot">
				<a onclick="createDept(3);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
			</div>
		</div>
		<div id="deptpanel_4" class="mypanel hide">

			<!-- List group -->
			<div class="list-group"></div>

			<br /> <br />

			<div class="myfoot">
				<a onclick="createDept(4);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
			</div>
		</div>
		<div id="deptpanel_5" class="mypanel hide">

			<!-- List group -->
			<div class="list-group"></div>

			<br /> <br />

			<div class="myfoot">
				<a onclick="createDept(5);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
			</div>
		</div>
	</div>

	<script src="./js/dept.js"></script>
</body>
</html>