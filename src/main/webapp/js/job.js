/**
 * 职级管理
 */

$(function() {
	queryJobList(-1);
});

function queryJobList(ifbase) {
	$.ajax({
		url : "./jobs/" + ifbase,
		type : 'POST',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				// set job list
				$("#baseJobListTable tbody").html('');
				$("#subJobListTable tbody").html('');
				for (var i = 0; i < resp.data.length; i++) {
					addJobItem(resp.data[i]);
				}

				setJobLevels();
			} else {
				bootbox.alert(resp.message);
			}
		},
		error : function(data, status, er) {
			loadUI.hidePleaseWait();
			bootbox.alert("数据请求出错：" + er);
		}
	});
}

function addJobItem(job) {
	if (job.ifBase == 0) {
		// 分会
		$("#subJobListTable tbody").append(
				'<tr data-id="'
				+ job.jobId
				+ '"><td>' + job.jobName
						+ '</td><td><select class="form-control" data-value="'
						+ job.jobLevel
						+ '" style="width:50px;"></select></td></tr>');

	} else {
		// 总会
		$("#baseJobListTable tbody")
				.append(
						'<tr data-id="'
								+ job.jobId
								+ '"><td>'
								+ job.jobName
								+ '</td><td><select class="form-control" data-value="'
								+ job.jobLevel
								+ '" style="width:50px;"></select></td><td><input type="checkbox" data-value="'
								+ job.ifViewBranch + '"/></td></tr>');
	}
}

function setJobLevels() {
	$('#baseJobListTable select, #subJobListTable select').each(
			function() {
				var selected = $(this).data('value');
				for ( var key in JOB_LEVEL_MAP) {
					$(this).append(
							'<option value="' + key + '" '
									+ (key == selected ? "selected" : "") + '>'
									+ JOB_LEVEL_MAP[key] + '</option>');
				}
				
				$(this).change(function() {
					saveJobLevel($(this).parents("tr:first").data('id'), $(this).val());
				});
			});

	$('#baseJobListTable input[type="checkbox"]').each(
			function() {
				var value = $(this).data('value');
				if (Number(value) > 0) {
					$(this).prop('checked', true);
				} else {
					$(this).prop('checked', false);
				}

				$(this).bootstrapToggle();

				$(this).change(
						function() {
							saveJobViewBranch($(this).parents("tr:first").data('id'),
									$(this).prop('checked'));
						});
			});
}

function saveJobLevel(jobId, level) {
	$.ajax({
		url : "./job/savelevel",
		data : {
			"jobId" : jobId,
			"jobLevel" : level
		},
		type : 'POST',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				
			} else {
				bootbox.alert(resp.message);
			}
		},
		error : function(data, status, er) {
			loadUI.hidePleaseWait();
			bootbox.alert("数据请求出错：" + er);
		}
	});
}

function saveJobViewBranch(jobId, checked) {
	$.ajax({
		url : "./job/branchview",
		data : {
			"jobId" : jobId,
			"viewBranch" : checked ? 1 : 0
		},
		type : 'POST',
		beforeSend : function() {
			loadUI.showPleaseWait();
		},
		success : function(resp) {
			loadUI.hidePleaseWait();
			if (resp.code == 0) {
				
			} else {
				bootbox.alert(resp.message);
			}
		},
		error : function(data, status, er) {
			loadUI.hidePleaseWait();
			bootbox.alert("数据请求出错：" + er);
		}
	});
}