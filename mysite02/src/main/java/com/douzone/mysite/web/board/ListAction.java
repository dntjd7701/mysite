package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardRepository bre = new BoardRepository();
		HashMap<String, Integer> map = new HashMap<>();
		
		// 데이터의 전체 갯수 
		int totalCount = bre.totalCount();
		// 총 페이지 수 
		int totalPage = bre.totalPage();

		// 페이지에 출력하고 싶은 수 
		int onePageCount = 5;
		// 시작 페이지 
		int startPage = 0;
		// 현재 페이지 
		String currentPage = request.getParameter("page");
		
		if(request.getParameter("page") != null) {
			startPage = (Integer.parseInt(currentPage)-1) * onePageCount ;
		}
		
		
		List<BoardVo> list = bre.findThisPage(startPage, onePageCount);
		
		
		request.setAttribute("lists", list);
		request.setAttribute("totalPage", totalPage);
		
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
