<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>美食商城后台管理</title>
		<link href="${basePath}/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.8.3.js"></script><%--basePath=webapp的地址导入jq1.83脚本 --%>
		<%-- Query的第一件事是：如果你想要一个事件运行在你的页面上，你必须在$(document).ready()里调用这个事件。
		所有包括在$(document).ready()里面的元素或事件都将会在DOM完成加载之后立即加载，并且在页面内容加载之前。 --%>
		<script type="text/javascript">
			$(document).ready(function() {
				location.href = "${basePath}/login";//location.href跳转页面  跳转至login.jsp
			});
		</script>
	</head>
	<body>
	</body>
</html>