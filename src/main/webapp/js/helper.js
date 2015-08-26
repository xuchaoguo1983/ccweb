/**
 * 一些公用数据或方法
 */

function getMapKeyByVal(map, val) {
	for ( var key in map) {
		if (map[key] == val)
			return key;
	}

	return null;
}

function initSelectOptions(select, map) {
	var control = $("#" + select);
	control.html('');
	for ( var key in map) {
		control.append('<option value="' + key + '">' + map[key] + '</option>');
	}
}

var ROOT_DEPT_ID = -1;// 部门根节点

// 成员状态
var MEMBER_STATUS_MAP = {
	'0' : '在职',
	'1' : '离职',
	'2' : '实习'
};

// 模板类型
var TEMPLATE_TYPE_MAP = {
	'1' : '费用',
	'2' : '出差',
	'3' : '请假',
	'5' : '用印',
	'9' : '其他'
};

var ANNOUCEMENT_TARGET_TYPE = {
	'2' : '部门',
	'3' : '职务'
};

var CHAMBER_INFO_TYPE = {
	'1' : '商会新闻',
	'2' : '通知公告',
	'3' : '商会党建',
	'4' : '供需发布',
	'5' : '会员风采',
	'6' : '关于我们'
};

var CHAMBER_INFO_SUBTYPE = {
	'1' : {
		'10' : '商会通知',
		'11' : '商会动态',
		'12' : '会务活动',
		'13' : '分会报道',
		'14' : '商会杂志',
		'15' : '社会公益'
	},
	'2' : {},
	'3' : {},
	'4' : {
		'40' : '供求信息',
		'41' : '集中采购',
		'42' : '招商引资',
		'43' : '招投标平台',
		'44' : '会员维权'
	},
	'5' : {
		'50' : '楚商风采',
		'51' : '楚商企业风采'
	},
	'6' : {
		'60' : '商会简介',
		'61' : '会长专栏',
		'62' : '领导团队',
		'63' : '执行团队',
		'64' : '常务副会长'
	}
};

function getChamberTypeName(chamberType) {
	for ( var key in CHAMBER_INFO_SUBTYPE) {
		if (key == chamberType) {
			return CHAMBER_INFO_TYPE[chamberType];
		} else {
			var subTypes = CHAMBER_INFO_SUBTYPE[key];
			for ( var subKey in subTypes) {
				if (subKey == chamberType) {
					return subTypes[subKey];
				}
			}

		}
	}

	return chamberType;
}

function checkAll(check) {
	var checkStatus = $(check).prop("checked");
	$("input[name='chk_list']").each(function() {
		if (checkStatus != $(this).prop("checked"))
			$(this).trigger('click');
	});
}

function getCheckedList() {
	var idArray = new Array();
	$("input[name='chk_list']").each(function() {
		if ($(this).prop("checked")) {
			idArray.push($(this).attr("id"));
		}
	});

	return idArray;
}

function setErrorMessage(divId, message) {
	var div = $('#' + divId);

	if (message != null && message != '') {
		div.removeClass('has-error').addClass('has-error');

		var errorBlock = div.find('.with-errors:first');
		if (errorBlock.length > 0)
			errorBlock.html('<ul class="list-unstyled"><li>' + message
					+ '</li></ul>');
		return true;
	} else {
		if (div.hasClass('has-error'))
			div.removeClass('has-error');

		var errorBlock = div.find('.with-errors:first');
		if (errorBlock.length > 0)
			errorBlock.html('');
		return false;
	}
}
