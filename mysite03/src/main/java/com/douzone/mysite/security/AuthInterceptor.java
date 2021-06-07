package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		
		// 1. handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			// DefaultServletHandler가 처리하는 경우(정적 자원 접근)
			return true;
		}
		// 2. casting 
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 받아오기 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		
		// 4. Handler Mehtod에 @Auth가 없다.
		if(auth == null) {
			return true;
		}
		
		// 5. @Auth가 붙어 있기 때문에 (Authentification) 여부 확인 
		HttpSession session = request.getSession();
		UserVo authUser = session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+ "/login");
		}
		
		// 조건절 따져보고 맞으면 뒤에 handler 실행시켜 ~
		return true;
	}

}
