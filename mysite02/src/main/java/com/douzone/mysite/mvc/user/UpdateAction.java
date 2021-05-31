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

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// session 받기 
		HttpSession session = request.getSession();
		boolean result = false;
		
		if(session == null) {
			return;
		}
		
		// UserVo로 session 정보를 통해, authUser 값을 입력 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		
		// 그에 대한 조건 검사가 필요함.
		
		
		// 이름을 null로 넣었을 때. 
		if(request.getParameter("name") == null) {
			MvcUtils.forward(request.getContextPath()+"user/updateform", request, response);
			
		}
		
		// update 쿼리 필요, UserRepository에 UpdateByNameGenderPassword 생성
		// 두 가지 사항이 필요함
		// 업데이트는 부분적으로 하는 것이 아닌 전체가 수정되어야함.
		// 즉, 업데이트는 name,gender,password 모두가 되어야함 
		// 단, 패스워드에는 기본값을 넣지 않았기 때문에, 
		// 패스워드가 변경되지 않았을 시엔 원래의 패스워드 값을 넣어주어야 함. 
		// 패스워드는 널값이 들어가지 않도록 해야함.
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String password = request.getParameter("password");
	
		
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setGender(gender);
			vo.setPassword(password);
			// 비교를 위해 authUser의 no값을 삽입, 비교값으로 이용됌. 
			vo.setNo(authUser.getNo());
			
			
			// 와.. 예술이다 
			result = new UserRepository().authUserUpdate(vo);
			if(result != false) {
				authUser.setName(name);
			}
			
			MvcUtils.redirect(request.getContextPath(), request, response);
		
	}
	
}

