package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// 사용자만 글쓰기를 보이게 해놨으므로 필요 없음.
//		if(session == null) {
//			return;
//		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if("".equals(title) || "".equals(content)) {
			MvcUtils.redirect(request.getContextPath() + "/board", request, response);
		}
		
		BoardRepository br = new BoardRepository();		
		int maxGNo = br.maxGroupNo();
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setGroupNo(maxGNo+1);
	
		
		// find에서 조인을 위해 user_no 삽입
		vo.setUserNo(authUser.getNo());
		
		new BoardRepository().insert(vo);
		
		MvcUtils.redirect(request.getContextPath() + "/board", request, response);
	}

}
