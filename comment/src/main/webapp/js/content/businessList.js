function remove(id) {
	$("#mainForm").attr("action",$("#basePath").val() + "/businesses/" + id);
	$("#mainForm").submit();
}

function search() {
	$("#mainForm").attr("method","GET");
	$("#mainForm").attr("action",$("#basePath").val() + "/businesses");
	$("#mainForm").submit();
}
//跳转页面，并非提交表单操作数据库
function modifyInit(id) {
	location.href = $("#basePath").val() + "/businesses/" + id;
}
