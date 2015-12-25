<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职级管理</title>
<link rel="stylesheet" href="./css/bootstrap-toggle.min.css">
</head>
<body>
	<div id="titlebar">
		<span class="title">职级管理</span>
	</div>
	
	<div id="jobDiv" class="container-fluid">
		<div class="row">
			<div class="col-xs-7">
				<div class="panel panel-default">
					<div class="panel-heading">总会职级管理</div>
					<div class="panel-body">
						<table id="baseJobListTable" class="table table-hover">
							<thead>
								<tr align="center">
									<th>职称</th>
									<th>职级</th>
									<th width="40%">是否可查看分会职级</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="col-xs-4">
				<div class="panel panel-default">
					<div class="panel-heading">分会职级管理</div>
					<div class="panel-body">
						<table id="subJobListTable" class="table table-hover">
							<thead>
								<tr align="center">
									<th>职称</th>
									<th>职级</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="./js/bootstrap-toggle.min.js"></script>	
	<script src="./js/job.js"></script>
</body>
</html>