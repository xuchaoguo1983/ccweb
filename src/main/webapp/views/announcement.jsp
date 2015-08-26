<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告管理</title>
<link rel="stylesheet" href="./css/daterangepicker.css">
</head>
<body>
	<div id="titlebar">
		<span class="title">公告管理</span>
		<div class="btn-group" data-toggle="buttons">
			<label class="btn"> <input type="radio"
				name="statuBtn" id="underVerifyFilterBtn" autocomplete="off"
				value="0"> 待审核
			</label> <label class="btn"> <input type="radio" name="statuBtn"
				id="verifiedFilterBtn" autocomplete="off" value="1"> 已发布
			</label> <label class="btn"> <input type="radio" name="statuBtn"
				id="rejectedFilterBtn" autocomplete="off" value="2"> 已驳回
			</label>
		</div>
		<span class="operation"> <a id="addAnnouncementLink"> <i
				class="fa fa-bullhorn fa-fw"></i>新建公告
		</a>
		</span>
	</div>

	<div id="querybar" class="container-fluid">
		<form class="form-inline">
			<div class="form-group">
				<label for="dateRange">发布日期：</label> <input type="text"
					class="form-control" style="width: 200px;" id="dateRange">
			</div>
		</form>
	</div>

	<div class="container-fluid">
		<div id="mylistview_1" class="list-group"></div>

		<!-- page bar -->
		<nav id="pagebar" class="pagebar" style="display:none;">
		<ul class="pagination">

		</ul>
		</nav>
	</div>

	<!-- verify dialog -->
	<div id="verifyModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">公告审核</h4>
				</div>
				<div class="modal-body">
					<form id="verifyform" class="form-horizontal" role="form">
						<input type="hidden" id="verifyId" name="verifyId" />
						<div class="form-group">
							<label for="status" class="col-sm-3 control-label">审核状态：</label>
							<div class="col-sm-5">
								<select id="verifyStatus" name="verifyStatus"
									class="form-control">
									<option value="1">审核通过</option>
									<option value="2">审核不通过</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="username" class="col-sm-3 control-label">审核意见：</label>
							<div class="col-sm-7">
								<textarea id="verifyDesc" name="verifyDesc" class="form-control"
								cols="2"></textarea>
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="verifyBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<script src="./js/moment.js"></script>
	<script src="./js/daterangepicker.js"></script>

	<script src="./js/page.js"></script>
	<script src="./js/announcement.js"></script>
</body>
</html>