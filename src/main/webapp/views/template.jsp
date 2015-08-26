<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>审批模版管理</title>
<link rel="stylesheet" href="./css/bootstrap-tagsinput.css">

</head>
<body>
	<div id="titlebar">
		<span class="title">审批模版管理</span>
	</div>

	<div class="container-fluid">
		<div class="row" style="padding: 15px 15px;">
			<div class="col-lg-4" style="padding: 0px;">
				<div id="deptpanel_1" class="mypanel">

					<!-- List group -->
					<div id="templateListGroup" class="list-group"></div>

					<br /> <br />

					<div class="myfoot">
						<a onclick="createTemplate();"><i class="fa fa-plus fa-fw"></i>创建新模版</a>
					</div>
				</div>

			</div>
			<div class="col-lg-4" style="padding: 0 5px;">
				<div id="deptpanel_2" class="mypanel hide">

					<!-- List group -->
					<div id="templateDeptListGroup" class="list-group"></div>

					<br /> <br />

					<div class="myfoot">
						<a onclick="addTemplateDept();"><i class="fa fa-plus fa-fw"></i>添加适用部门</a>
					</div>
				</div>

			</div>
			<div class="col-lg-4" style="padding: 0px;">
				<div id="deptpanel_3" class="mypanel hide">

					<!-- List group -->
					<div id="templateDeptApprovalListGroup" class="list-group"></div>

					<br /> <br />

					<div class="myfoot">
						<a onclick="addTemplateDeptApproval();"><i
							class="fa fa-plus fa-fw"></i>添加审批人</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- edit dialog -->
	<div id="templateModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">模板编辑</h4>
				</div>
				<div class="modal-body">
					<form id="templateform" class="form-horizontal" role="form"
						data-toggle="validator">
						<div class="form-group">
							<input type="hidden" id="templateId" name="templateId" /> <label
								for="templateName" class="col-sm-3 control-label">模板名称：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="templateName"
									name="templateName" data-error="请输入模板名称" required>
								<div class="help-block with-errors"></div>
							</div>
						</div>

						<div class="form-group">
							<label for="status" class="col-sm-3 control-label">模板类型：</label>
							<div class="col-sm-5">
								<select id="templateType" name="templateType"
									class="form-control">

								</select>
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="saveTemplateBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- dept select dialog -->
	<div id="templateDeptModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">添加适用部门</h4>
				</div>
				<div class="modal-body">
					<form id="templatedeptform" class="form-inline">
						<div id="targetDepts" class="form-group">
							<label for="deptname" class="control-label">适用部门：</label> <select
								class="form-control input-sm dept_1"></select> <select
								class="form-control input-sm dept_2"></select> <select
								class="form-control input-sm dept_3"></select>
						</div>

						<div class="checkbox">
							<label> <input id="alldeptsChx" data-toggle="tooltip"
								data-placement="top" title="勾选标示该模板应用于所有部门" type="checkbox">所有部门
							</label>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="addTemplateDeptBtn" type="button"
						class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- add approval dialog -->
	<div id="templateDeptApprovalModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">添加审批人</h4>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="col-sm-12">
								<form class="form-inline">
									<div id="approvalDepts" class="form-group">
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
							<nav id="pagebar" class="pagebar" style="display: none;">
								<ul class="pagination">

								</ul>
							</nav>
						</div>

						<div class="row">
							<div class="col-sm-12">
								<label for="selectedMembers">已选审批人：</label>
							</div>
							<div class="col-sm-12">
								<input id="selectedMembers" type="text" class="form-control"/>
							</div>
						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="addTemplateDeptApprovalBtn" type="button"
						class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<script src="./js/jquery.cxselect.min.js"></script>
	<script src="./js/bootstrap-tagsinput.min.js"></script>
	<script src="./js/page.js"></script>
	<script src="./js/template.js"></script>
</body>
</html>