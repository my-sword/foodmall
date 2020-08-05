package com.xzb.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xzb.util.CommonUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
//每次提交请求依次执行拦截器
public class AuthInterceptor implements HandlerInterceptor {
	/**
	 * 在进入Handler方法执行之前执行本方法
	 * 
	 * @return true:执行下一个拦截器，直到所有拦截器都执行完，再执行被拦截的Controller
	 *         false:从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//CommonUtil.contains:判断session中存放的actiondto列表中是否包含指定的url和method方式
		//request.getServletPath()获取请求项目下路径:/main/list.jsp  request.getMethod() ==GET或POST
		//因为是根据用户具有权限行为进行匹配 action表中必须要有 sys_group_action和sys_action与sys_user匹配的请求地址和方法(二级菜单默认是GET)
		if (CommonUtil.contains(request.getSession(), request.getServletPath(), request.getMethod())) {
			return true;
		}
		// 针对ajax请求处理
		if (request.getHeader("x-requested-with") != null) {
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			response.setHeader("url", basePath + "/login/noAuth");//跳转到服务器的noAuth请求方法
		} else {
			request.getRequestDispatcher("/login/noAuth").forward(request, response);
		}
		return false;
	}

	/**
	 * 在进入Handler方法之后，返回ModelAndView之前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 在Handler方法执行完之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		// TODO Auto-generated method stub

	}

}