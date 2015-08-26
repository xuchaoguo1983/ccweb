/**
 * 登陆
 */

$(function() {
	//$('#verifySuccessTips').hide();

	$('#qrcodeLoginLink').click(function() {
		$('#normalLoginDiv').fadeOut(500, function() {
			$('#codeLoginDiv').fadeIn(0, function() {
				// refresh the qr code
				loadQRCode();

				// listen the server login status
				listenQRCodeLogin();
			});
		});
	});

	$('#accountLoginLink').click(function() {
		$('#codeLoginDiv').fadeOut(500, function() {
			$('#normalLoginDiv').fadeIn();
		});
	});

	function loadQRCode() {
		$('#codeLoginDiv img').attr('src',
				'./login/qrcode?timestamp=' + new Date().getTime());
	}

	function listenQRCodeLogin() {
		if ($('#codeLoginDiv').css("display") == "none") {
			return false;
		}

		setTimeout(function() {
			$.ajax({
				url : "./login/qrcode",
				type : 'POST',
				success : function(resp) {
					if (resp.code == 0) {
						$('#accountLoginLink').hide();
						$('#verifySuccessDiv').show();
						location.href = "./depts";
					} else {
						if (resp.code == -9999) {
							// 条码已失效，重新刷新
							loadQRCode();
						}

						listenQRCodeLogin();
					}
				},
				error : function(data, status, er) {
					listenQRCodeLogin();
				}
			});
		}, 30000);
	}

});