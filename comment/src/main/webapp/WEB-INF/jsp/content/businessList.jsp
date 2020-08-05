<!-- 菜单子项：商户管理--商户列表-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
		<title></title>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/all.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/pop.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css"/>
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${basePath}/js/content/businessList.js"></script>
	</head>
	<body style="background: #e1e9eb;">
    <!-- 页面表单 通过businessList.js修改action提交地址 -->
		<form action="${basePath}/businesses/search" id="mainForm" method="post">
			<input type="hidden" name="_method" value="DELETE"/>
			<input type="hidden" id="basePath" value="${basePath}"/>
			<input type="hidden" name="page.currentPage" id="currentPage" value="1"/>
			<div class="right">
				<div class="current">当前位置：<a href="#">内容管理</a> &gt; 商户管理</div>
				<div class="rightCont">
					<p class="g_title fix">商户列表</p>
					<table class="tab1">
						<tbody>
							<tr>
								<td align="right" width="80">标题：</td>
								<td>
									<input name="title" id="title" value="${searchParam.title}" class="allInput" type="text"/>
								</td>
	                            <td style="text-align: right;" width="150">
                                    <!-- 查询按钮 -->
	                            	<input class="tabSub" value="查询" onclick="search('1');" type="button"/>&nbsp;&nbsp;&nbsp;&nbsp;
	                            	<t:auth url="/businesses/addPage" method="GET">
	                            		<input class="tabSub" value="添加" onclick="location.href='${basePath}/businesses/addPage'" type="button"/>
	                            	</t:auth>
	                            </td>
	       					</tr>
						</tbody>
					</table>
					<div class="zixun fix">
						<table class="tab2" width="100%">
							<tbody>
								<tr>
								    <th>序号</th>
								    <th>标题</th>
								    <th>副标题</th>
								    <th>城市</th>
								    <th>类别</th>
								    <th>操作</th>
								</tr>
								<!-- 商户详情列表 list由model传输 varStatus为变量递增数 -->
								<c:forEach items="${list}" var="item" varStatus="s">
									<tr>
										<td>${s.index + 1}</td>
										<td>${item.title}</td>
										<td>${item.subtitle}</td>
										<td>${item.cityDic.name}</td>
										<td>${item.categoryDic.name}</td>

                                        <!-- auth.tag 使用jsp命令调用类函数用于判断session中存放的actiondto列表中是否包含指定的url、用于二级菜单权限管理 -->
										<td>
											<t:auth url="/businesses/${item.id}" method="PUT"><!-- 确定提交方式用于接口提交 -->
                                                <!-- void(0)计算为0,用户点击不会发生任何效果 相当于return false; -->
												<a href="javascript:void(0);" onclick="modifyInit('${item.id}')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
											</t:auth>
											<t:auth url="/businesses/${item.id}" method="DELETE">
												<a href="javascript:void(0);" onclick="remove('${item.id}')">删除</a>
											</t:auth>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

                        <!-- 分页 JSP的page.tag模块 模块名：jsMethodName调用js方法 searchParam.page调用数据  -->
						<t:page jsMethodName="search" page="${searchParam.page}"></t:page>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>