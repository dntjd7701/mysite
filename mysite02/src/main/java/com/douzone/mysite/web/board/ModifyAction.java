package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String modifiedTitle = request.getParameter("title");
		String modifiedContent = request.getParameter("content");
		Long no = Long.parseLong(request.getParameter("no"));
		
		new BoardRepository().UpdateTitleAndContent(no, modifiedTitle, modifiedContent);
		
		
		MvcUtils.redirect(request.getContextPath() + "/board", request, response);
	}

}
