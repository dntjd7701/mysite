package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// DB에서의 값과 비교
		UserVo userVo =new UserRepository().findByEmailAndPassword(email, password);
		if(userVo == null) {
			//setAttribute 안에는 객체여야하지만
			// new Boolean(false)로 오토 빡싱
			request.setAttribute("result", "fail");
			
			request.setAttribute("email", email);
			
			MvcUtils.forward("user/loginform", request, response);
			return ;
		}
		
		
//		System.out.println(userVo);
		// 인증처리(session 처리)
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", userVo);
		
		
		//Main으로 리다이렉트
		MvcUtils.redirect(request.getContextPath(), request, response);
		
		
//		System.out.println(email + ":" + password);
	}

}
