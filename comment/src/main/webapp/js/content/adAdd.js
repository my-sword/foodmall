$(function() {
	common.showMessage($("#message").val());
});
//提交
function add() {
	if(check()) {
		$("#mainForm").submit();
	}
}
//额外验证
function check() {
	// TODO 需要添加表单验证
	return true;
}
//“返回”跳转
function goback() {
	location.href = $('#basePath').val() + '/ad';
}