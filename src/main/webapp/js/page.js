/**
 * 导航分页
 * 
 * @param data
 *            ：对应服务器pageMeta数据
 * @param containerId
 *            page的容器id
 * @param func
 *            分页点击触发方法
 */

function setPageUI(data, containerId, func) {
	if (data.totalRows == 0) {
		$("#" + containerId).hide();
	} else {
		$("#" + containerId).show();

		var curPage = data.page;
		var totalPage = data.totalPage;

		var html = '';

		var startPage = curPage - 2;
		if (startPage < 1)
			startPage = 1;
		var endPage = curPage + 2;
		if (endPage > totalPage)
			endPage = totalPage;
		// the previous btn
		if (startPage < curPage) {
			html += '<li><a page="'
					+ (curPage - 1)
					+ '" aria-label="上一页"> <span aria-hidden="true">&laquo;</span></a></li>';
		} else {
			html += '<li class="disabled"><a aria-label="上一页"> <span aria-hidden="true">&laquo;</span></a></li>';
		}

		for (var i = startPage; i <= endPage; i++) {
			if (i == curPage) {
				html += '<li class="active"><a>' + i + '</a></li>';
			} else {
				html += '<li><a page="' + i + '">' + i + '</a></li>';
			}
		}
		// the next btn
		if (curPage < endPage) {
			html += '<li><a page="'
					+ (curPage + 1)
					+ '" aria-label="下一页"> <span aria-hidden="true">&raquo;</span></a></li>';
		} else {
			html += '<li class="disabled"><a aria-label="下一页"> <span aria-hidden="true">&raquo;</span></a></li>';
		}

		$("#" + containerId + " ul").html(html);
	}

	$("#" + containerId + " ul li a").each(function() {
		$(this).click(function() {
			var pageIndex = $(this).attr('page');
			if (pageIndex == null || pageIndex == 'undefined') {
				return false;
			}

			func(Number(pageIndex));
		});
	});
}