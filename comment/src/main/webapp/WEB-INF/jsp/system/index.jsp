<!-- 后台管理主界面 -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
	    <title>美食商城后台管理</title>

		<%--  href=地址 {jsp地址变量}，ref=CSS样式表--%>
	    <link href="${basePath}/css/all.css" rel="stylesheet" type="text/css"/>
	    <link href="${basePath}/css/pop.css" rel="stylesheet" type="text/css"/>
	    <link href="${basePath}/css/index.css" rel="stylesheet" type="text/css"/><!-- 蒙版DIV布局设置 -->

	    <script src="${basePath}/js/common/jquery-1.8.3.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/common.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/json.js" type="text/javascript"></script>
	    <script src="${basePath}/js/system/index.js" type="text/javascript"></script>
	</head>
	<body>
		<!-- 蒙版DIV 修改密码的div盒子-->
		<div id="mengban" style="display:none"></div>
		<input type="hidden" id="basePath" value="${basePath}"/>
		<!--   display:none可以隐藏某个元素,且隐藏的元素不会占用任何空间。 left距离左上角位置 -->
		<div class="wishlistBox" style="display: none;left:550px;top:200px;">
		    <div class="personRigTop persongBgimg" style="height:200px;width:480px;">
		        <div class="persongRightTit" style="width:480px;">&nbsp;&nbsp;修改密码</div><!-- 使用图片分层 -->
		        <div class="persongRigCon">
		            <form name="redisAddOrEditForm" action="#" method="post">
		                <table class="x-form-table">
		                    <tbody>
		                    <tr class="line"><!-- em强调的内容 .x-form-table tbody tr td.left em.required CSS与优先级有关 -->
		                        <td class="left" width="10%"><em class="required">*</em><label>原始密码：</label></td>
		                        <td width="90%">
		                            <input class="normal-input" name="oldPassword" id="oldPassword" style="width: 240px;" type="password"/>
		                        </td>
		                    </tr>
		                    <tr class="line">
		                        <td class="left"><label><em class="required">*</em>新密码：</label></td>
		                        <td>
		                            <input class="normal-input" name="newPassword" id="newPassword" style="width: 240px;" type="password"/>
		                        </td>
		                    </tr>
		                    <tr class="line">
		                        <td class="left"><em class="required">*</em><label>确认新密码：</label></td>
		                        <td>
		                            <input class="normal-input" name="newPasswordAgain" id="newPasswordAgain" style="width: 240px;" type="password"/>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td class="left"></td>
		                        <td class="submit">
		                            <input id="submitVal" class="tabSub" value="提交" onclick="checkForm('${basePath}/comment');" type="button"/>
		                            <input class="tabSub" value="关闭" onclick="closeDiv();" type="reset"/>
									<!-- 通过index.js设置closeDiv();("#mengban").css("visibility","hidden");设置隐藏 -->
		                        </td>
		                    </tr>
		                    </tbody>
		                </table>

		            </form>
		        </div>
		    </div>
		</div>

		<!-- 提交时(退出系统提交) 跳转session接口  -->
		<form method="post" action="${basePath}/session" id="mainForm">
			<input type="hidden" name="_method" value="DELETE"/>
			<!-- 右上角设置栏 -->
		    <div id="header">
		        <div class="iheader">
		            <div class="logo"><a href="#"><img src="" alt="" height="88px" width="99px"/></a> </div>
		            <div style="height: 44px;">
		                <div class="wuxianlogo"><img src="" alt="" height="28px" width="275px"/></div>
		                <div class="h_info">
		                    <span class="line"></span>
							欢迎您！
							<c:forEach items="${admin}" var="item" >
								${item.name}
							</c:forEach>
							当前时间： <b id="bb">2020年1月20日&nbsp;&nbsp;&nbsp;&nbsp;</b>
		                    <a href="javascript:void(0);" onclick="openAddDiv();">[修改密码]</a>
		                    &nbsp;
		                    <a href="javascript:void(0);" onclick="if(confirm('您确认退出系统?')){$('#mainForm').submit();};">[退出系统]</a>
		                </div>
		            </div>
		            <ul class="nav" id="menuDiv">
		            </ul>
		        </div>
		    </div>

			<!-- 容器显示页面数据地址 -->
		    <div id="container">
		        <table style="vertical-align:top" cellspacing="0" cellpadding="0" bgcolor="#e1e9eb" border="0">
		            <tbody>
		            <tr>
		                <td class="leftTd" style="vertical-align:top" width="150">
		                    <div class="left">
		                        <div class="ileft" id="subMenuDiv">
		                        	
		                        </div>
		                    </div>
		                </td>
		                <td width="7">
		                    <div class="pointer"></div>
		                </td>
		                <td style="vertical-align:top" height="600px" width="100%">
		                	<br/><iframe id="mainPage" src="" frameborder="0" height="580px" width="100%"></iframe><br/>
		                 </td>
		            </tr>
		            </tbody>
		        </table>
		    </div>
		    <div id="footer">
		    </div>
		</form>
	</body>
</html>