package com.xzb.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

	/**
	 * 登录成功后，后台管理首页
	 */
	@RequestMapping//没有参数 是默认为("/index")
	public String init() {
		return "/system/index";//此处不用index.jsp因为在applicationContext-web.xml中配置了
		// <property name="prefix" value="/WEB-INF/jsp"/>
		// <property name="suffix" value=".jsp"/>

	}
}
