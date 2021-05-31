package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// map에 묶어서, 넣으면됌.
		
//		
//		totalPage = 
//		firstPageNo = 3
//		lastPageNo = 7
//		nextPageNo  = 8
//		prevPageNo  = 2
//		currentPage = 4
//		
//		map = new request..
//		map.put("lastPageNo",lastPageNo)
//		
//		request.setAttribute("pageInfo", map);
		
		MvcUtils.forward("board/list", request, response);
	}

}
