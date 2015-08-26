<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./css/bootstrap-tagsinput.css">
<title>外勤记录</title>
</head>
<body>
	<div id="titlebar">
		<span class="title">外勤记录</span>
	</div>

	<div id="querybar" class="container-fluid">
		<div class="row"></div>
		<div class="col-sm-5">
			<input id="selectedMembers" type="text" class="form-control" />
		</div>
		<div class="col-sm-2">
			<button id="userSelBtn" class="btn btn-default" type="button">成员筛选</button>
		</div>
	</div>

	<div class="container-fluid">
		<div id="mylistview_2" class="list-group"></div>

		<!-- page bar -->
		<nav id="pagebar" class="pagebar" style="display: none;">
			<ul class="pagination">

			</ul>
		</nav>
	</div>

	<!-- user select dialog -->
	<div id="memberSelectModal" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">成员筛选</h4>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="col-sm-12">
								<form class="form-inline">
									<div id="seldepts" class="form-group">
										<label for="deptname">部门：</label> <select
											class="form-control input-sm dept_1"></select> <select
											class="form-control input-sm dept_2"></select> <select
											class="form-control input-sm dept_3"></select>
									</div>
								</form>
							</div>
						</div>
						<br />
						<div class="row">
							<div class="col-sm-12">
								<table id="mytable" class="table table-condensed table-hover">
									<thead>
										<tr>
											<th width="5%;"><input id="checkall"
												onclick="checkAll(this);" type="checkbox"></th>
											<th width="20%;">姓名</th>
											<th>公司名称</th>
											<th width="15%">公司职位</th>
											<th width="12%">状态</th>
										</tr>
									</thead>
									<tbody>
										<tr align="center">
											<td colspan="5">请先选择部门</td>
										</tr>
									</tbody>
								</table>
							</div>

							<!-- page bar -->
							<nav id="pagebar2" class="pagebar" style="display: none;">
								<ul class="pagination">

								</ul>
							</nav>
						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="setSelectUserBtn" type="button" class="btn btn-primary">筛选</button>
				</div>
			</div>
		</div>
	</div>

	<script src="./js/jquery.cxselect.min.js"></script>
	<script src="./js/bootstrap-tagsinput.min.js"></script>
	<script src="./js/page.js"></script>
	<script src="./js/checkout.js"></script>

</body>
</html>