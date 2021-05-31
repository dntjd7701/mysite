package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 그에 대한 조건 검사가 필요함.
		
		
		// 이름을 null로 넣었을 때. 
		if(request.getParameter("name") == null) {
			
			MvcUtils.forward(request.getContextPath()+"user/updateform", request, response);
		}
		
		
		// 업데이트 성공 시, 메인 화면으로 이동
		MvcUtils.forward(request.getContextPath(), request, response);
	}

}
