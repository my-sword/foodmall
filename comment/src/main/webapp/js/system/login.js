$(function() {//在login.jsp引入该js,页面加载自启动
	// 调用common.showMessage。密码验证错误时,服务器传参数pageCode.msg,获取message加载的枚举值调用alert方法)
	common.showMessage($("#message").val());//val() 方法返回或设置被选元素的 value 属性。

    // 验证信息(表单的name和password框不能为空)
	$("#mainForm").validate({
		rules:{
			"name" : {
				required : true
			},
			"password" : {
				required : true
			}
		}
	});
	
	// 单击登录
	$("#submit_login").click(function () {
		if($("#password").val()) {//如果字符串不为空
			$("#password_md5").val($.md5($("#password").val()));//jquery.md5.js插件 doller.(md5("你想要加密的字符串"));
		}
		$("#mainForm").submit();
    });
	// 绑定enter事件
	$("#password").keydown(function(event) {
		console.log(event.keyCode)
		if ( event.keyCode === 13 ) {//keycode为enter键
			if($("#password").val()) {//如果字符串不为空
				$("#password_md5").val($.md5($("#password").val()));//jquery.md5.js插件 doller.(md5("你想要加密的字符串"));
			}
			$("#mainForm").submit();
		}

	})
});


