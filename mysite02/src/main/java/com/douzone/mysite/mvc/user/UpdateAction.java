package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		//session이 null 이면 로그인 안하고 들어온거임
		// 접근제어 (인증이 필요한 접근에 대한 체크 
		if(session == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		// 이건 꼭!!!!있어야함 
		// 로그인이 되었는지 확인하는거임. UserVo가 null값이면 로그인 안한거기 때문. 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		
		Long userNo = authUser.getNo();
		//UserVo userVo = new UserRepository().findByNo(userNo);
//		request.setAttribute("userVo", userVo);
		
		MvcUtils.forward("user/updateform", request, response);
	}

}
