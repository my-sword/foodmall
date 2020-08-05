package com.xzb.controller.system;

import com.xzb.constant.DicTypeConst;
import com.xzb.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private DicService dicService;

	@RequestMapping
	public String index(Model model) {
		//model可以提供前台调用  与session不同的是 model只提供接下来页面的调用
		model.addAttribute("httpMethodList", dicService.getListByType(DicTypeConst.HTTP_METHOD));//"httpMethod"==DicTypeConst.HTTP_METHOD
		return "/system/auth";//跳转到auth.jsp权限管理 iframe 页面
	}
}