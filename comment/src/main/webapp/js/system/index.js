// 当前登录用户可以访问的菜单Map
var menuMap = {};

$(function() {//页面加载时刷新请求ajax(菜单名在数据库中)
	common.ajax({
			url : $("#basePath").val() + "/session/menus",//向后台请求地址
			success : function(data) {//成功则获取数据
				if(data && data.length > 0) {
					$.each(data,function(i,value) {//对菜单键值对进行循环  i是循环下标  value是所有数据对象
						if(!menuMap[value.parentId]) {//如果父级id不为0  则为空数组
							menuMap[value.parentId] = [];
						}
						menuMap[value.parentId].push(value);//添加父级菜单为0的138对象给menuMap二维数组  即 menuMap[0]有三个一维：menuMap[2]
					});
					initMenu();//初始化菜单
				}
			}
	});

	console.log(new Date().toLocaleDateString());
	$('#bb').html(new Date().toLocaleDateString()+'&nbsp&nbsp&nbsp')
});

/**
 * 初始化一级菜单
 */
function initMenu() {
	var menuList = menuMap[0];
	$("#menuDiv").html("");
	$.each(menuList,function(i,value) {
		//插入的值 value.id是对应的1,3,8父级菜单 value.name是对应的 系统、内容、统计管理
		$("#menuDiv").append("<li onclick='clickMenu(this," + value.id + ")'><a><span>" + value.name + "</span></a></li>");
	});
}
/**
 * 方法描述:单击菜单（页面上部菜单），初始化子菜单（即页面左部菜单）
 */
function clickMenu(element,id) {
	// 将同级节点的[选中样式]清空
	$("#menuDiv").children().attr("class","");
	// 将当前单击的节点置为[选中样式]
	$(element).attr("class","on");
	// 加载子菜单内容
	initSubMenu(id);
}

/**
 * 根据父菜单ID初始化子菜单（即页面左部菜单）
 */
function initSubMenu(parentId) {
	var menuList = menuMap[parentId];
	$("#subMenuDiv").html("");//清空页面左部菜单所有布局
	$.each(menuList,function(i,value) {
		$("#subMenuDiv").append("<h3 onclick=\"clickSubMenu(this,'" + value.url + "')\"><a>" + value.name + "</a></h3>");
	});
}

/**
 * 方法描述:单击子菜单（页面左部菜单），初始化主页面
 */
function clickSubMenu(element,path) {
	// 将其他有[选中样式]的节点的样式清空
	$("#subMenuDiv").find(".on").attr("class","");
	// 将当前单击的节点置为[选中样式]
	$(element).children().attr("class","on");
	// 按指定地址加载菜单的页面(iframe)  访问服务器地址+url对应的接口
	$("#mainPage").attr("src",$("#basePath").val()+ path);
}

/**
* 打开密码修改弹出层
*/
function openAddDiv(){
	$("#mengban").css("visibility","visible");
	$(".wishlistBox").show();
	//find 可以寻找元素集合 find('li')或者子元素  .on函数 .类名等
	$(".wishlistBox").find(".persongRightTit").html("&nbsp;&nbsp;修改密码");
	$("#submitVal").show();
}

/**
* 关闭密码修改弹出层
*/
function closeDiv(){
	$("#mengban").css("visibility","hidden");
	$("#oldPassword").val("");
	$("#newPassword").val("");
	$("#newPasswordAgain").val("");
	$(".wishlistBox").hide();
}

