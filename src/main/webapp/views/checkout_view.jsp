<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外勤记录</title>
<style>
#map-container {
	height: 300px
}
</style>
</head>
<body>
	<div id="titlebar">
		<a id="backlink"><i class="fa fa-chevron-left fa-fw"></i>返回</a> <span
			class="title2">外勤详情</span>
	</div>

	<div id="previewDiv" class="container-fluid">
		<div class="row">
			<div class="col-xs-7">
				<div class="panel panel-default mypanel">
					<div class="panel-heading">外勤记录</div>
					<div class="panel-body">
						<p>
							客户名称：<span> ${checkout.clientName } </span>
						</p>
						<p>
							外勤内容：<span>${checkout.content}</span>
						</p>
						<p>
							外勤地点：<span>${checkout.outPlace} <c:if
									test="${not empty checkout.outPlace}">
									<a onclick="showMap('${checkout.outPlace}')"><i
										class="fa fa-map-marker fa-fw"></i></a>
								</c:if></span>
						</p>
						<p>
							外勤时间：<span>${checkout.outTime}</span>
						</p>
						<p>
							知会人：<span>${checkout.informUserNames}</span>
						</p>

						<c:set value="${ fn:split(checkout.pictures, ',') }"
							var="pictures" />
						<c:if test="${pictures != null && fn:length(pictures) > 0}">
							<hr />
							<div id="myCarousel" class="carousel slide" data-ride="carousel">
								<ol class="carousel-indicators">
									<c:forEach items="${ pictures }" varStatus="status">
										<c:choose>
											<c:when test="${status.index==0}">
												<li data-target="#myCarousel" data-slide-to="0"
													class="active"></li>
											</c:when>
											<c:otherwise>
												<li data-target="#myCarousel"
													data-slide-to="${status.index }"></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</ol>
								<!-- 轮播（Carousel）项目 -->
								<div class="carousel-inner" role="listbox">

									<c:forEach items="${ pictures }" var="picture"
										varStatus="status">
										<c:choose>
											<c:when test="${status.index==0}">
												<div class="item active">
													<img src="${ picture}">
												</div>
											</c:when>
											<c:otherwise>
												<div class="item">
													<img src="${ picture}">
												</div>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>

							</div>
						</c:if>
					</div>
				</div>
				<div id="allmap"></div>

			</div>

			<div class="col-xs-5">
				<div id="infobar" style="top: 0px; right: 0px; position: relative;">
					<div class="title">外勤人</div>
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
							<div class="col-xs-4 left">职位：</div>
							<div id="positionCell" class="col-xs-8 right"></div>
						</div>

						<div class="row">
							<div class="col-xs-4 left">状态：</div>
							<div id="statusCell" class="col-xs-8 right"></div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="mapModal" role="dialog" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">地图定位</h4>
				</div>
				<div class="modal-body">
					<div id="map-container"></div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="http://api.map.baidu.com/api?v=2.0&ak=F1e63b718ab387c934d21a1cda087d86"></script>
	<script type="text/javascript">
		$(function() {
			$('#backlink').click(function() {
				history.go(-1);
			});

			//读取外勤人信息
			$.ajax({
				url : "./member/" + '${checkout.userId}',
				data : {},
				type : 'GET',
				beforeSend : function() {
					loadUI.showPleaseWait();
				},
				success : function(resp) {
					loadUI.hidePleaseWait();
					if (resp.code == 0) {
						setMemberInfo(resp.data);
					} else {
						bootbox.alert(resp.message);
					}
				},
				error : function(data, status, er) {
					loadUI.hidePleaseWait();
					bootbox.alert("数据请求出错：" + er);
				}
			});
		});

		function setMemberInfo(data) {
			if (data == null) {
				// it should not happen
				bootbox.alert('获取成员信息失败');
				return false;
			}

			$("#infobar #userImageCell").attr("src", data.userImage);
			$("#infobar #userNameCell").html(data.userName);
			$("#infobar #mobileCell").html(data.mobile);
			$("#infobar #positionCell").html(data.position);
			$("#infobar #statusCell").html(MEMBER_STATUS_MAP[data.status]);
		}

		var map = null;
		function showMap(address) {
			$('#mapModal').modal('show');
			$('#mapModal').on('shown.bs.modal', function (event) {
				// 百度地图API功能
				if (map == null) {
					map = new BMap.Map("map-container");

					var point = new BMap.Point(116.331398, 39.897445);
					map.centerAndZoom(point, 12);
					// 创建地址解析器实例
					var myGeo = new BMap.Geocoder();
					// 将地址解析结果显示在地图上,并调整地图视野
					myGeo.getPoint(address, function(point) {
						if (point) {
							map.centerAndZoom(point, 16);
							map.addOverlay(new BMap.Marker(point));
						} else {
							bootbox.alert("定位该地址失败!");
						}
					}, "全国");
				}
			});
		}
	</script>
</body>
</html>