<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成员信息审核</title>
<style type="text/css">
#infochangediv .row {
	padding-bottom: 10px;
}
</style>
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
					<div class="panel-heading">信息修改详情</div>
					<div class="panel-body">
						<div id="infochangediv" class="container-fluid">
							<div class="row">
								<div id="srcMemberDiv" class="col-xs-6">
									<div class="row">
										<div class="col-xs-12 left">
											<h4>原始信息：</h4>
										</div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">用户姓名：</div>
										<div id="userNameCell0" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">手机号码：</div>
										<div id="mobileCell0" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">办公电话：</div>
										<div id="officeTellCell0" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">Email地址：</div>
										<div id="emailCell0" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">籍贯：</div>
										<div id="birthPlaceCell0" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">公司名称：</div>
										<div id="companyNameCell0" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">公司职务：</div>
										<div id="positionCell0" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">公司地址：</div>
										<div id="addressCell0" class="col-xs-8 right"></div>
									</div>
								</div>
								<div id="targetMemberDiv" class="col-xs-6">
									<div class="row">
										<div class="col-xs-12 left">
											<h4>修改信息：</h4>
										</div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">用户姓名：</div>
										<div id="userNameCell1" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">手机号码：</div>
										<div id="mobileCell1" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">办公电话：</div>
										<div id="officeTellCell1" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">Email地址：</div>
										<div id="emailCell1" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">籍贯：</div>
										<div id="birthPlaceCell1" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">公司名称：</div>
										<div id="companyNameCell1" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">公司职务：</div>
										<div id="positionCell1" class="col-xs-8 right"></div>
									</div>

									<div class="row">
										<div class="col-xs-4 left">公司地址：</div>
										<div id="addressCell1" class="col-xs-8 right"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="footbtns">
		<button id="verifyBtn" type="button" class="btn btn-primary">审核</button>
	</div>

	<div id="verifyModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">修改记录审核</h4>
				</div>
				<div class="modal-body">
					<form id="verifyform" class="form-horizontal">
						<input type="hidden" id="companyId" name="companyId"/>
						<input type="hidden" id="userId" name="userId"/>
						<input type="hidden" id="recordId" name="recordId"/>
						<div class="form-group">
							<label for="verifyStatus" class="col-sm-3 control-label">审核状态：</label>
							<div class="col-sm-5">
								<select id="verifyStatus" name="verifyStatus" class="form-control">

								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="verifyDesc" class="col-sm-3 control-label">审核意见：</label>
							<div class="col-sm-9">
								<textarea rows="3" id="verifyDesc" name="verifyDesc" class="form-control"></textarea>
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

	<script src="./js/member_audit.js"></script>
</body>
</html>