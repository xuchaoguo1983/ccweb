<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成员管理</title>
</head>
<body>
	<div id="titlebar">
		<span class="title">成员管理</span> <span class="operation"> <a
			id="moveMemberLink"> <i class="fa fa-exchange fa-fw"></i>移动到
		</a> <a id="addMemberLink"> <i class="fa fa-user-plus fa-fw"></i>添加
		</a> <a id="importMemberLink"> <i class="fa fa-download fa-fw"></i>批量导入
		</a> <a id="removeMemberLink"> <i class="fa fa-trash-o fa-fw"></i>删除
		</a>
		</span>
	</div>

	<div id="querybar" class="container-fluid">
		<form class="form-inline">
			<div id="depts" class="form-group">
				<label for="deptname">部门：</label> <select
					class="form-control input-sm dept_1"></select> <select
					class="form-control input-sm dept_2"></select> <select
					class="form-control input-sm dept_3"></select>
			</div>
		</form>
	</div>

	<div class="container-fluid">
		<div id="tablecontainer">
			<table id="mytable" class="table table-hover">
				<thead>
					<tr>
						<th width="5%;"><input id="checkall"
							onclick="checkAll(this);" type="checkbox"></th>
						<th width="10%;">姓名</th>
						<th width="12%">手机号码</th>
						<th>公司名称</th>
						<th width="15%">公司职位</th>
						<th width="12%">状态</th>
						<th width="10%">籍贯</th>
					</tr>
				</thead>
				<tbody>
					<tr align="center">
						<td colspan="7">请先选择部门</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div id="infobar" style="display: none;">
			<div class="title">
				成员信息 <a onclick="closeInfoBar();"><i class="fa fa-times"></i></a> <a
					id="editMemberLink"><i class="fa fa-edit"></i></a>
			</div>
			<div id="headinfo">
				<img id="userImageCell" class="img-circle">
				<p id="userNameCell"></p>
				<p id="userDeptCell"></p>
			</div>
			<div id="detailinfo" class="container-fluid">
				<div class="row">
					<div class="col-xs-4 left">手机号码：</div>
					<div id="mobileCell" class="col-xs-8 right"></div>
				</div>

				<div class="row">
					<div class="col-xs-4 left">办公电话：</div>
					<div id="officeTellCell" class="col-xs-8 right"></div>
				</div>

				<div class="row">
					<div class="col-xs-4 left">公司名称：</div>
					<div id="companyNameCell" class="col-xs-8 right"></div>
				</div>

				<div class="row">
					<div class="col-xs-4 left">公司地址：</div>
					<div id="addressCell" class="col-xs-8 right"></div>
				</div>


				<div class="row">
					<div class="col-xs-4 left">职位：</div>
					<div id="positionCell" class="col-xs-8 right"></div>
				</div>

				<div class="row">
					<div class="col-xs-4 left">状态：</div>
					<div id="statusCell" class="col-xs-8 right"></div>
				</div>

				<div class="row">
					<div class="col-xs-4 left">籍贯：</div>
					<div id="birthPlaceCell" class="col-xs-8 right"></div>
				</div>
			</div>
		</div>

		<!-- page bar -->
		<nav id="pagebar" class="pagebar" style="display: none;">
			<ul class="pagination">

			</ul>
		</nav>
	</div>

	<!-- edit dialog -->
	<div id="memberModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">成员编辑</h4>
				</div>
				<div class="modal-body">
					<form id="memberform" class="form-horizontal" role="form"
						data-toggle="validator">
						<input type="hidden" id="userId" name="userId" value="" />
						<div class="form-group">
							<label for="username" class="col-sm-3 control-label">姓名：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="userName"
									name="userName" data-error="请输入姓名" required>
								<div class="help-block with-errors"></div>
							</div>
						</div>
						<div class="form-group">
							<label for="mobile" class="col-sm-3 control-label">手机号码：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="mobile"
									name="mobile" pattern="^([0-9]{11})?$" data-error="请输入正确的手机号码"
									required>
								<div class="help-block with-errors"></div>
							</div>
						</div>

						<div class="form-group">
							<label for="officeTell" class="col-sm-3 control-label">办公电话：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="officeTell"
									name="officeTell">
							</div>
						</div>

						<div class="form-group">
							<label for="companyName" class="col-sm-3 control-label">公司名称：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="companyName"
									name="companyName">
							</div>
						</div>

						<div class="form-group">
							<label for="address" class="col-sm-3 control-label">公司地址：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="address"
									name="address">
							</div>
						</div>

						<div class="form-group">
							<label for="position" class="col-sm-3 control-label">公司职位：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="position"
									name="position">
							</div>
						</div>

						<div class="form-group">
							<label for="status" class="col-sm-3 control-label">状态：</label>
							<div class="col-sm-5">
								<select id="status" name="status" class="form-control">

								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="birthPlace" class="col-sm-3 control-label">籍贯：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="birthPlace"
									name="birthPlace">
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="saveBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- dept move dialog -->
	<div id="deptMoveModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">成员批量移动</h4>
				</div>
				<div class="modal-body">
					<form id="deptmoveform" class="form-inline">
						<div id="targetDepts" class="form-group">
							<label for="deptname">目标部门：</label> <select
								class="form-control input-sm dept_1"></select> <select
								class="form-control input-sm dept_2"></select> <select
								class="form-control input-sm dept_3"></select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="moveDeptBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- member import -->
	<!-- dept move dialog -->
	<div id="memberImportModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">成员批量导入</h4>
				</div>
				<div class="modal-body">
					<form id="memberimportform" class="form-horizontal">
						<div class="form-group">
							<p class="col-sm-11 col-sm-offset-1">
								第一步：下载<a href="./files/template_001.xlsx">《成员导入模版》</a>
							</p>
						</div>
						<div class="form-group">
							<p class="col-sm-11 col-sm-offset-1">第二步：根据模版编辑数据然后上传文件</p>
						</div>
						<div class="form-group">
							<input class="col-sm-11 col-sm-offset-1" type="file" id="file"
								name="file">
						</div>

						<div id="progress" class="progress">
							<div class="progress-bar progress-bar-success"></div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="memberImportBtn" type="button" class="btn btn-primary">上传</button>
				</div>
			</div>
		</div>
	</div>

	<script src="./js/jquery.cxselect.min.js"></script>
	<script src="./js/jquery.ui.widget.js"></script>
	<script src="./js/jquery.iframe-transport.js"></script>
	<script src="./js/jquery.fileupload.js"></script>

	<script src="./js/page.js"></script>
	<script src="./js/member.js"></script>
</body>
</html>