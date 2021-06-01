package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long matchNo = Long.parseLong(request.getParameter("no"));
		request.getParameter("userNo");
		
		
		List<BoardVo> viewInfo = new BoardRepository().findByViewInfo(matchNo);
		
		
		request.setAttribute("viewInfos", viewInfo);
		MvcUtils.forward("board/view", request, response);
	}

}
