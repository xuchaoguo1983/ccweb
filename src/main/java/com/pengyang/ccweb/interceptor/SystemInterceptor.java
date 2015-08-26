package com.pengyang.ccweb.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 会话失效拦截器
 * 
 * @author xuchaoguo
 * 
 */
public class SystemInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {
				// 是ajax请求，则返回个消息给前台
				PrintWriter printWriter = response.getWriter();
				printWriter.print("{sessionState:timeout}");
				printWriter.flush();
				printWriter.close();
			} else {
				response.sendRedirect(request.getContextPath());
			}
			return false;
		}

		return super.preHandle(request, response, handler);
	}

}
