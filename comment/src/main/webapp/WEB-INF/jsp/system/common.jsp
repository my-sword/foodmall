<%  //内部可用java注释语句
    //通用变量 协议+服务名+端口号+工程名=webapp的地址("http://localhost:8082/d14c3o_war_exploded")
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    request.setAttribute("basePath", basePath);//将变量的值放入request里面，由index.jsp等调用 {basePath}
%>
<!-- 这里的common文件由web.xml自动装载-->




