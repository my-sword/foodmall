package com.xzb.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xzb.constant.SessionKeyConst;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * session拦截器
 */
public class SessionInterceptor implements HandlerInterceptor {

	/**
	 * 在进入Handler方法执行之前执行本方法
	 * 
	 * @return true:执行下一个拦截器，直到所有拦截器都执行完，再执行被拦截的Controller
	 *         false:从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 */
	//防止没验证登录其它页面
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		///validate验证成功后设置了字段映射 存在说明验证成功
		if (request.getSession().getAttribute(SessionKeyConst.USER_INFO) != null) {
			return true;//可以执行控制器的跳转
		}

		//跳转到session超时的错误页面    针对ajax请求处理
		//在服务器端判断request来自Ajax请求(异步(局部刷新))还是传统请求(同步(整个页面全部等待))。
		if (request.getHeader("x-requested-with") != null) {//x-requested-with为异步请求时
			//request.getScheme() 返回当前链接使用的协议；一般应用返回http;SSL返回https;  域名:端口
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			response.setHeader("url", basePath + "/login/sessionTimeout");//返回请求头--到服务端的LoginController类执行
		} else {
			request.getRequestDispatcher("/login/sessionTimeout").forward(request, response);
		}
		return false;//表示不可执行控制器（不可执行的内容由xml自定义拦截器<mvc:interceptors>设置）
	}


	/**
	 * 在进入Handler方法之后，返回ModelAndView之前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 在Handler方法执行完之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}