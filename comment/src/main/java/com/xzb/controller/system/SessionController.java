package com.xzb.controller.system;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.xzb.constant.SessionKeyConst;
import com.xzb.dto.MenuDto;
import com.xzb.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * session相关，从session中获取存放的资源
 */
@Controller
@RequestMapping("/session")
public class SessionController {
	private  Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HttpSession session;
	//单击菜单
	@SuppressWarnings("unchecked")//告诉编译器忽略 unchecked 警告信息，如使用List，ArrayList等未进行参数化产生的警告信息
	@RequestMapping(value = "/menus",method = RequestMethod.GET)//只处理get的请求
	@ResponseBody//将java对象转为json格式的数据。
	public List<MenuDto> getUserMenuList() {//id name url parentId orderNum actionList是menuDto成员
		log.debug("\n成功 getUserMenuList");
		System.out.println("成功");
		log.debug(String.valueOf(session.getAttribute(SessionKeyConst.USER_INFO)));

		//登录已存在的session 返回SessionKeyConst.MENU_INFO映射对应的List<MenuDto>数据( LoginController的/validate定义)
		return (List<MenuDto>)session.getAttribute(SessionKeyConst.MENU_INFO);//将用户所能访问的菜单信息的Key值 以List<MenuDto>存在session中
	}



	/**
	 * 退出系统
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public String signOut() {
		session.invalidate();//将session设置为失效，拦截器HandlerInterceptorAdapter重新拦截
		return "redirect:/login";
	}
}
