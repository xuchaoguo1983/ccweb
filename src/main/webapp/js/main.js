/**
 * 框架基本功能
 */

var loadUI;

$(function() {
	$('#side-menu').metisMenu();

	loadUI = loadUI || (function() {
		var pleaseWaitDiv = $('#loadingmodel');
		return {
			showPleaseWait : function() {
				pleaseWaitDiv.modal('show').css({
					'margin-top' : function() { // vertical centering
						return ($(this).height() / 2) - 100;
					}
				});
			},
			hidePleaseWait : function() {
				pleaseWaitDiv.modal('hide');
			},

		};
	})();

	var docHeight = 0;
	// update the height every 200ms
	window.setInterval(function() {
		var newDocHeight = $(document).height();
		if (docHeight != newDocHeight) {
			docHeight = newDocHeight;

			$(".sidebar").css("min-height", (docHeight) + "px");
		}
	}, 200);

	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		cache : false,
		complete : function(data, TS) {
			if (data.sessionState == 'timeout') {
				// session out
				top.location.href = "./";
			}
		}
	});
});

// Loads the correct sidebar on window load,
// collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
	$(window)
			.bind(
					"load resize",
					function() {
						width = (this.window.innerWidth > 0) ? this.window.innerWidth
								: this.screen.width;
						if (width < 768) {
							$('div.navbar-collapse').addClass('collapse');
						} else {
							$('div.navbar-collapse').removeClass('collapse');
						}

						height = ((this.window.innerHeight > 0) ? this.window.innerHeight
								: this.screen.height);

						$("#page-wrapper").css("min-height", (height) + "px");
						$(".sidebar").css("min-height", (height) + "px");
					});

	// init side menus
	var chamberMenu = $('#menu_chamberinfo').next();
	chamberMenu.html('');
	for ( var key in CHAMBER_INFO_TYPE) {
		chamberMenu.append('<li><a id="chamberinfo_"' + key
				+ ' href="./chamberinfo/' + key
				+ '"><i class="fa fa-caret-right fa-fw"></i>'
				+ CHAMBER_INFO_TYPE[key] + '</a></li>');
	}

	var url = window.location;
	var element = $('ul.nav a').filter(function() {
		return this.href == url || url.href.indexOf(this.href) == 0;
	}).addClass('active').parent().parent().addClass('in').parent();
	if (element.is('li')) {
		element.addClass('active');
	}
	
	baseUrl = $('base').attr('href');
});

