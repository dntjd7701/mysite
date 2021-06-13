package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVo;

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
		
		// 4. Handler Mehtod에 @Auth가 없으면 Type에 붙어 있는지 확인한다.(과제)
		
		if(auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		
		if(auth == null) {
			return true;
		}
		/**		과제 
		 * if(auth == null){
		 * 		auth = handlerMethod.?
		 * }
		 * 
		 *		>> Handler Method에 있음.   
		 */
		
		// 5. Type 이나 Method 둘 다 @Auth가 적용이 안되어 있는 경우. 
		// 이건 진짜 guestbook 같은거 ㅇㅇ 
		// 전체로 하려면, handlerClass에서 찾는거 메소드가 있음. 
		// 그걸 찾아야함 
		
		// 6. @Auth가 붙어 있기 때문에 (Authentification) 여부 확인
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath()+ "/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+ "/user/login");
			return false;
		}
		// 조건절 따져보고 맞으면 뒤에 handler 실행시켜 ~
		
		// 7.Authorization(권한) Check, @Auth의 role 가져오기("ADMIN" or "USER")
		String role = auth.role();
		// role과 authUser.getRole을 비교. 
		// 단, admin으로 로그인되어있을경우에는 다 들어갈 수 있어야함. 
		// 비교해서 admin page로 보내기 ~
		if("USER".equals(role)) {
			return true;
		}
		if("ADMIN".equals(authUser.getRole())) {
			response.sendRedirect(request.getContextPath()+ "/user/login");
			return false;
		}
			return true;
	}

}
