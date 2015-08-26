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
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>我的商会</title>

<!-- Bootstrap -->
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./css/bootstrap.css">
<link rel="stylesheet" href="./css/login.css">
<link rel="stylesheet" href="./css/font-awesome.min.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
body {
	background-color: #352e3c;
}
</style>

</head>

<body>

	<div class="container" style="margin-top: 120px;">

		<div class="row">
			<div class="col-xs-5" align="right" style="padding: 0px;">
				<div class="title-signin">
					<span>我</span> <span>的</span> <span>商</span> <span>会</span>
				</div>
			</div>
			<div class="col-xs-7" style="padding: 0px; z-index: 0">
				<form class="form-signin" role="form" data-toggle="validator"
					action="./login" method="post">
					<div class="form-group">
						<table style="margin: 0 auto;">
							<tr>
								<td>
									<hr style="width: 50px;" />
								</td>
								<td><span>&nbsp;&nbsp;网页版登陆&nbsp;&nbsp;</span></td>
								<td>
									<hr style="width: 50px;" />
								</td>
							</tr>
						</table>
					</div>
					<div id="normalLoginDiv">
						<div class="form-group">
							<label for="phoneNo">手机号码</label> <input type="text"
								class="form-control" id="phone" name="phone"
								value="${login.phone }" data-error="请输入手机号码" required>
							<div class="help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="password">密码</label> <input type="password"
								class="form-control" id="password" name="password"
								data-error="请输入密码" required>
							<div class="help-block with-errors"></div>
						</div>
						<br />

						<div class="form-group">
							<button type="submit"
								class="btn btn-primary ladda-button btn-lg btn-block"
								data-style="expand-left">
								<span class="ladda-label">登 陆</span>
							</button>
						</div>

						<div class="form-group">
							<a id="qrcodeLoginLink" class="btn btn-default center-block"
								href="#"><i class="fa fa-qrcode fa-fw"></i><label>扫码登陆</label></a>
						</div>

						<div class="form-group">
							<a id="forgetPassLink" class="pull-right" href="#"><label>忘记密码？</label></a>
							<br />
						</div>
					</div>

					<div id="codeLoginDiv" style="display: none;">
						<div class="form-group">
							<img class="center-block" style="width: 230px; height: 230px;" />
						</div>

						<div class="form-group">
							<div id="verifySuccessTips"
								class="alert alert-success center-block" role="alert"
								style="display: none;">
								<p>扫码验证通过，正在跳转...</p>
							</div>
						</div>

						<div class="form-group">
							<a id="accountLoginLink" class="btn btn-default center-block"><i
								class="fa fa-sign-in fa-fw"></i><label>账户登陆</label></a>
						</div>
					</div>

				</form>


			</div>
		</div>

	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/validator.min.js"></script>
	<script src="./js/bootbox.js"></script>
	<script src="./js/login.js"></script>

	<!-- error handling -->
	<c:if test="${not empty error}">
		<script type="text/javascript">
			bootbox.alert('${error}');
		</script>
	</c:if>

</body>
</html>