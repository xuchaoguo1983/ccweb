<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title><sitemesh:write property='title' /></title>

<!-- Bootstrap -->
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./css/bootstrap.css">
<!-- MetisMenu CSS -->
<link rel="stylesheet" href="./css/metisMenu.css">

<link rel="stylesheet" href="./css/main.css">

<link rel="stylesheet" href="./css/font-awesome.min.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<sitemesh:write property='head' />

<!-- Bootstrap core JavaScript
    ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<!-- Metis Menu Plugin JavaScript -->
<script src="./js/metisMenu.min.js"></script>
<script src="./js/validator.min.js"></script>
<script src="./js/bootbox.js"></script>

<!-- Custom Theme JavaScript -->
<script src="./js/helper.js"></script>
<script src="./js/main.js"></script>

</head>

<body>
	<div id="wrapper">
		<div class="navbar-default sidebar" role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>

			<div class="sidebar-nav navbar-collapse">
				<div id="logo">${ sessionScope.user.companyName}</div>

				<div id="userinfo">
					<div class="uleft">
						<img style="width: 60px; height: 60px;"
							src="${sessionScope.user.adminImage }" class="img-circle">

					</div>
					<div class="uright">
						<p style="font-size: 18px;"><span class="adminName">
							${ sessionScope.user.adminName}</span> <a id="editUserInfoLink"><i
								class="fa fa-edit fa-fw"></i> </a>
						</p>

						<p class="adminJobName" style="font-size: 14px;">${ sessionScope.user.jobName}</p>
					</div>
				</div>

				<ul class="nav" id="side-menu">
					<li></li>
					<li><a id="menu_depts" href="./depts"><i
							class="fa fa-sitemap fa-fw"></i> 部门管理</a></li>
					<li><a id="menu_jobs" href="./jobs"><i
							class="fa fa-diamond fa-fw"></i> 职级管理</a></li>		
					<li><a id="menu_users" href="./members"><i
							class="fa fa-users fa-fw"></i> 成员管理</a></li>
					<li><a id="menu_templates" href="./templates"><i
							class="fa fa-list-alt fa-fw"></i> 审批模版管理</a></li>
					<li><a id="menu_notes" href="./announcement"><i
							class="fa fa-bullhorn fa-fw"></i> 公告管理</a></li>

					<li><a id="menu_notes" href="./checkout"><i
							class="fa fa-calendar fa-fw"></i> 外勤记录</a></li>

					<li><a id="menu_chamberinfo" href="#"><i
							class="fa fa-files-o fa-fw"></i> 商会管理<span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<!-- init by script. check main.js-->
						</ul> <!-- /.nav-second-level --></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12 navbar-top-links">
						<!-- /.navbar-header -->
						<div class="nav navbar-right">
							<form class="form-inline">
								<div class="form-group">
									<div class="input-group">
										<input type="text" class="form-control" placeholder="搜索...">
										<span class="input-group-btn">
											<button class="btn btn-default" type="button">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</div>
								<a id="configLink"><span><i class="fa fa-cog fa-fw"></i>设置</span>
								</a> <a href="./logout"> <span><i
										class="fa fa-power-off fa-fw"></i>退出</span>
								</a>
							</form>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-12" style="padding: 0px;">
						<sitemesh:write property='body' />
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<div id="loadingmodel" class="modal fade" data-backdrop="static"
		data-keyboard="false">
		<div class="modal-dialog modal-sm">
			<div class="modal-content"
				style="height: 50px; line-height: 50px; text-align: center; font-size: 16px; color: #333333;">
				<i class="fa fa-spinner fa-pulse fa-lg"></i>正在加载，请稍后...
			</div>
		</div>
	</div>
	
	<!-- edit dialog -->
	<div id="curUserModel" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">个人信息编辑</h4>
				</div>
				<div class="modal-body">
					<form id="curuserform" class="form-horizontal" role="form"
						data-toggle="validator">
						<input type="hidden" id="curUserId" name="userId" value="${sessionScope.user.adminId}"/>
						<div class="form-group">
							<label for="cur_username" class="col-sm-3 control-label">姓名：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="cur_userName"
									name="userName" data-error="请输入姓名" required>
								<div class="help-block with-errors"></div>
							</div>
						</div>
						<div class="form-group">
							<label for="cur_mobile" class="col-sm-3 control-label">手机号码：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="cur_mobile"
									name="mobile" pattern="^([0-9]{11})?$" data-error="请输入正确的手机号码"
									required>
								<div class="help-block with-errors"></div>
							</div>
						</div>

						<div class="form-group">
							<label for="cur_officeTell" class="col-sm-3 control-label">办公电话：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="cur_officeTell"
									name="officeTell">
							</div>
						</div>

						<div class="form-group">
							<label for="cur_companyName" class="col-sm-3 control-label">公司名称：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="cur_companyName"
									name="companyName">
							</div>
						</div>
												
						<div class="form-group">
							<label for="cur_address" class="col-sm-3 control-label">公司地址：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="cur_address"
									name="address">
							</div>
						</div>

						<div class="form-group">
							<label for="cur_position" class="col-sm-3 control-label">公司职位：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="cur_position"
									name="position">
							</div>
						</div>

						<div class="form-group">
							<label for="cur_status" class="col-sm-3 control-label">状态：</label>
							<div class="col-sm-5">
								<select id="cur_status" name="status" class="form-control">
									
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="cur_birthPlace" class="col-sm-3 control-label">籍贯：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="cur_birthPlace"
									name="birthPlace">
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="saveCurUserBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- error handling -->
	<c:if test="${not empty error}">
		<script type="text/javascript">
			bootbox.alert('${error}');
		</script>
	</c:if>
	
	<script src="./js/user.js"></script>
</body>
</html>