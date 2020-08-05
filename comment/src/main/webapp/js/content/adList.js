$(function() {
	common.showMessage($("#message").val());
});

//搜索的功能 设置tag脚本获取当前指定页码
function search(currentPage) {
	$("#currentPage").val(currentPage);
	$("#mainForm").submit();
}

//重定义指向
function remove(id) {
	if(confirm("确定要删除这条广告吗？")) {
		$("#id").val(id);
		$("#mainForm").attr("action",$("#basePath").val() + "/ad/remove");
		$("#mainForm").submit();
	}
}


function modifyInit(id) {
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/ad/modifyInit");
	$("#mainForm").submit();
}