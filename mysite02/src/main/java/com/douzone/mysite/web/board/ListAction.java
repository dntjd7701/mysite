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
		
		
		// 현재 페이지 
		int currentPage = 1;
		
		if(request.getParameter("page") != null) {
			currentPage = (Integer.parseInt(request.getParameter("page")));
		}
		
		int startPage = (currentPage-1)*onePageCount;
		
		int count = totalCount-startPage;
		
		
		int firstPage = currentPage >= 3 ? currentPage-2 : 1;
		int lastPage = currentPage >= 3 ? (currentPage+2>=totalPage?totalPage:currentPage+2) : (5 > totalPage?totalPage:5);
		
		// 왼쪽, 오른쪽
		int prevPage = currentPage-1;
		if(prevPage <= 1) {
			prevPage = 1;
		}
		int nextPage = currentPage+1;
		if(nextPage >= totalPage) {
			nextPage = totalPage;
		}
		
		if(totalPage == 0) {
			lastPage = currentPage;
		}
		
		map.put("currentPage", currentPage);
		map.put("firstPage", firstPage);
		map.put("lastPage", lastPage);
		map.put("startPage", startPage);
		map.put("totalPage", totalPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("count", count);
		
		List<BoardVo> list = bre.findThisPage(startPage, onePageCount);
		
		request.setAttribute("lists", list);
		request.setAttribute("map", map);
		
		
		MvcUtils.forward("board/list", request, response);
	}

}
