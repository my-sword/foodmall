package com.xzb.controller.system;

import javax.servlet.http.HttpSession;

import com.xzb.constant.PageCodeEnum;
import com.xzb.constant.SessionKeyConst;
import com.xzb.dto.GroupDto;
import com.xzb.dto.UserDto;
import com.xzb.service.GroupService;
import com.xzb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 登录相关
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private HttpSession session;

	/**
	 * 默认跳转登录页面
	 */
	@RequestMapping
	public String index() {
		return "/system/login";
	}

	/**
	 * session超时
	 */
	@RequestMapping("/sessionTimeout")
	public String sessionTimeout(Model model) {
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.SESSION_TIMEOUT);//"pageCode"和SESSION_TIMEOUT(1302,"session超时，请重新登录！")
		return "/system/error";//返回system/error.jsp页面
	}

	/**
	 * 没有权限访问
	 */
	@RequestMapping("/noAuth")
	public String noAuth(Model model) {//model给视图层添加映射属性 类似于HttpServletRequest用于传值  model和session作用范围不同
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.NO_AUTH);
		session.invalidate();
		return "/system/error";
	}

	/**
	 * 验证用户名/密码是否正确 验证通过跳转至后台管理首页，验证失败，返回至登录页。
	 */
	@RequestMapping("/validate")
	public String validate(UserDto userDto, RedirectAttributes attr) {//RequestMapping特性，将表单内容对应的name值自动赋给UserDto
		if (userService.validate(userDto)) {//UserDao.xml的select查询成功返回1 验证通过
			session.setAttribute(SessionKeyConst.USER_INFO, userDto);//设置映射 字段为"USER_INFO"字符串(以此验证是否拦截)，值为userDto
			//在此处 调用业务功能的事务  寻找sys_menu的id  (debug可查看调用过程)
			GroupDto groupDto = groupService.getByIdWithMenuAction(userDto.getGroupId());//resultType="User"  1
			//直接放入session中并由前端index.js的ajax调用
			//设置session对应的关系映射  提供SessionController调用SessionKeyConst.MENU_INFO返回ajax调用
			session.setAttribute(SessionKeyConst.MENU_INFO,groupDto.getMenuDtoList());
			session.setAttribute(SessionKeyConst.ACTION_INFO, groupDto.getActionDtoList());

			return "redirect:/index";
		}
		//addFlashAttribute于重定向之后还能带参数跳转.隐藏了参数，链接地址上不直接暴露，但是能且只能在重定向的 “页面” 获取prama参数值。
		attr.addFlashAttribute(PageCodeEnum.KEY, PageCodeEnum.LOGIN_FAIL);
		return "redirect:/login";
	}
}