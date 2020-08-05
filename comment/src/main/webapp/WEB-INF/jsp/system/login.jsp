<!-- 后台管理登录界面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>美食商城后台登录</title>
	    <link rel="stylesheet" type="text/css" href="${basePath}/css/login.css" />
		<!-- 引入jquery的样式 jQuery是一个快速、简洁的JavaScript框架 -->
	    <link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.validate.css" />
		<!-- 引入jQuery文件 -->
	    <script src="${basePath}/js/common/jquery-1.8.3.js" type="text/javascript"></script>
		<!-- md5加密文档 -->
	    <script src="${basePath}/js/common/jQuery.md5.js" type="text/javascript"></script>
		<!-- 表单验证框架 -->
	    <script src="${basePath}/js/common/validation/jquery.validate.min.js" type="text/javascript"></script>
		<!-- 表单错误信息提示 -->
	    <script src="${basePath}/js/common/validation/messages_zh.js" type="text/javascript"></script>
		<!-- 通用插件：弹出信息框、ajax二次封装、页面返回码定义，与后台PageCode定义对应 -->
		<!-- AJAX 是与服务器交换数据并更新部分网页的技术 -->
	    <script src="${basePath}/js/common/common.js" type="text/javascript"></script>
		<!-- login.js包含弹出信息、验证信息、单击登陆功能  -->
	    <script src="${basePath}/js/system/login.js" type="text/javascript"></script>
	</head>
	<body>
		<input type="hidden" id="basePath" value="${basePath}"/>
		<!-- 错误信息 在login.js中将"mainForm"与"message"关联  "hidden"该类型主要为提供数据隐藏-->
		<input type="hidden" id="message" value="${pageCode.msg}"/><!-- pageCode是服务器传的值 -->
		<div class="main"><!--  <div> 块标签 可以把文档分割为独立的、不同的部分。 -->
		    <div class="header hide"></div>
		    <div class="content">
		        <div class="title hide"></div>
				<!-- method提交方式post(安全容量大) action将信息提交到指定位置(服务器的validate虚拟拦截器)-->
		        <form id="mainForm" method="post" action="${basePath}/login/validate"><!--  -->
		            <!-- 使用<fieldset>在一组控件周围建立边框，表明这些控件是相关的并用<legend>为由<fieldset>元素圈起的一组控件添加标题。 -->
					<fieldset>
		                <div class="input">
							<!-- 文本框 实现焦点事件显示和隐藏用户名 -->
							<!-- class规定元素的类名 name类型的名字 id引用唯一id placeholder悬置的文本  type控件类型  onFocus onBlur焦点事件-->
							<!-- class 属性大多数时候用于指向样式表中的类（class）。不过，也可以利用它通过 JavaScript 来改变带有指定 class 的 HTML 元素。 -->
		                    <input class="input_all name" name="name" id="name" placeholder="用户名" type="text" onFocus="this.className='input_all name_now';" onBlur="this.className='input_all name'"/>
		                </div>
		                <div class="input">
							<!-- 对密码进行md5加密 -->
		                	<input type="hidden" name="password" id="password_md5"/>
		                    <input class="input_all password" id="password" type="password" placeholder="密码" onFocus="this.className='input_all password_now';" onBlur="this.className='input_all password'"/>
		                </div>

		                <div class="checkbox"><!-- 使用<label>关联id="remember"  利用<span> 来组合行内元素(复选框和文字同一行) -->
		                    <!-- 记住密码没有功能 -->
                            <input type="checkbox" name="remember" id="remember" /><label for="remember"><span>记住密码</span></label>
		                </div>
		                <div class="enter"><!-- 登录按钮 -->
		                    <input class="button hide" type="button" id="submit_login" value="登录" />
		                </div>
		            </fieldset>
		        </form>
		    </div>
		</div>
	</body>
</html>