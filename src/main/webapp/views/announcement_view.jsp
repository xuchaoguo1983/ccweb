<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告详情</title>
</head>
<body>
	<div id="titlebar">
		<a id="backlink"><i class="fa fa-chevron-left fa-fw"></i>返回</a> <span
			class="title2">公告详情</span>
	</div>

	<div id="previewDiv">
		<p class="title">${announcement.title }</p>
		<p class="subtitle">
			<span>发布人：${announcement.publisherName}</span><span>发布时间：${announcement.publishTime }</span>
		</p>

		<div id="contentdiv">${announcement.content }</div>
		<hr />
		<div class="panel panel-default">
			<div class="panel-heading">通知对象</div>
			<div class="panel-body">
				<p>
					通知类型：<span> <c:choose>
							<c:when test="${announcement.target == 2}">
									部门
								</c:when>
							<c:otherwise>
									职务
								</c:otherwise>
						</c:choose>
					</span>
				</p>
				<p>
					通知对象：<span>${announcement.targetNames }</span>
				</p>
			</div>
		</div>

		<c:if test="${announcement.status!=0 && announcement.status!=9}">
			<div class="panel panel-default">
				<div class="panel-heading">审核记录</div>
				<div class="panel-body">
					<p>
						审核人：<span>${announcement.verifyUserName }</span>
					</p>
					<p>
						审核时间：<span>${announcement.verifyTime }</span>
					</p>
					<p>
						审核意见：<span>${announcement.verifyDesc }</span>
					</p>
					<p>
						审核状态：<span> <c:choose>
								<c:when test="${announcement.status == 1}">
									<span class="label label-success">审核通过</span>
								</c:when>
								<c:otherwise>
									<span class="label label-danger">审核不通过</span>
								</c:otherwise>
							</c:choose>
						</span>
					</p>
				</div>
			</div>
		</c:if>
	</div>



	<script type="text/javascript">
		$(function() {
			$('#backlink').click(function() {
				history.go(-1);
			});
		});
	</script>
</body>
</html>