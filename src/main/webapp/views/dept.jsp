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
	</div>

	<div class="container-fluid">
		<div class="row" style="padding: 15px 15px;">
			<div class="col-lg-4" style="padding: 0px;">
				<div id="deptpanel_1" class="mypanel">

					<!-- List group -->
					<div class="list-group"></div>

					<br /> <br />

					<div class="myfoot">
						<a onclick="createDept(1);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
					</div>
				</div>

			</div>
			<div class="col-lg-4" style="padding: 0 5px;">
				<div id="deptpanel_2" class="mypanel hide">

					<!-- List group -->
					<div class="list-group"></div>

					<br /> <br />

					<div class="myfoot">
						<a onclick="createDept(2);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
					</div>
				</div>

			</div>
			<div class="col-lg-4" style="padding: 0px;">
				<div id="deptpanel_3" class="mypanel hide">

					<!-- List group -->
					<div class="list-group"></div>

					<br /> <br />

					<div class="myfoot">
						<a onclick="createDept(3);"><i class="fa fa-plus fa-fw"></i>创建新部门</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="./js/dept.js"></script>
</body>
</html>