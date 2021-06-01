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

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		
		Long authUserNo = authVo.getNo();
		
		
		String replyTitle = request.getParameter("title");
		String replyContent = request.getParameter("content");
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		int orderNo = Integer.parseInt(request.getParameter("orderNo"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		
		if("".equals(replyTitle) || "".equals(replyContent)) {
			MvcUtils.redirect(request.getContextPath() + "/board", request, response);
		}

		BoardVo vo = new BoardVo();
		vo.setTitle(replyTitle);
		vo.setContents(replyContent);
		vo.setGroupNo(groupNo);
		vo.setOrderNo(orderNo+1);
		vo.setDepth(depth+1);
		vo.setUserNo(authUserNo);
		
		
		new BoardRepository().updateNo(vo);
		new BoardRepository().insert(vo);
		
		MvcUtils.redirect(request.getContextPath() + "/board", request, response);
	}

}
