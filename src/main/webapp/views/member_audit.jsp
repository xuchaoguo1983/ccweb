<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成员信息审核</title>
</head>
<body>
	<div id="titlebar">
		<a id="backlink"><i class="fa fa-chevron-left fa-fw"></i>返回</a> <span
			class="title2">审核个人信息修改</span>
	</div>

	<div id="previewDiv" class="container-fluid">
		<div class="row">
			<div class="col-xs-3">
				<div class="panel panel-default">
					<div class="panel-heading">申请列表</div>
					<div class="panel-body">
						<table id="applyListTable" class="table table-hover">
							<tbody>
								<tr align="center">
									<td colspan="2">暂无待审核信息</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="col-xs-9">
				<div class="panel panel-default">
					<div class="panel-heading"></div>
					<div class="panel-body">
						<div id="infochangediv" class="container-fluid">
							<div class="row">
								<div id="srcMemberDiv" class="col-xs-6"></div>
								<div id="targetMemberDiv" class="col-xs-6"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="./js/member_audit.js"></script>
</body>
</html>