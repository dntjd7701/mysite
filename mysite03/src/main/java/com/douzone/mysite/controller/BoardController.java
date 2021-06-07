package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "board/list";
	}
	
	
	
	@RequestMapping(value = "/{p}", method = RequestMethod.GET)
	public String index(@PathVariable("p") String p, Model model) {
		HashMap<String, Integer> map = new HashMap<>();

		int currentPage;
		if (p != null) {
			currentPage = Integer.parseInt(p);
		} else {
			currentPage = 1;
		}

		int totalCount = boardService.getTotalCount();
		int totalPage = boardService.getTotalPage();

		
		int onePageCount = 5;
		int startPage = (currentPage - 1) * onePageCount;
		int count = totalCount - startPage;
		int lastPage = currentPage >= 3 ? (currentPage + 2 >= totalPage ? totalPage : currentPage + 2)
				: (5 > totalPage ? totalPage : 5);
		int firstPage = currentPage >= 3 ? currentPage - 2 : 1;
		if (currentPage + 2 >= totalPage) {
			firstPage = totalPage - 4;
		}
		// 왼쪽, 오른쪽
		int prevPage = currentPage - 1;
		if (prevPage <= 1) {
			prevPage = 1;
		}
		int nextPage = currentPage + 1;
		if (nextPage >= totalPage) {
			nextPage = totalPage;
		}
		if (totalPage == 0) {
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
		map.put("onePageCount", onePageCount);

		List<BoardVo> list = boardService.getList(map);

		model.addAttribute("lists", list);
		model.addAttribute("map", map);

		return "board/list";
	}
}
