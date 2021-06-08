package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model) {
		HashMap<String, Integer> map = new HashMap<>();

		int currentPage=1;
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
	
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model) {
		 List<BoardVo> viewInfos = boardService.viewList(no);
		 
		 for(BoardVo vo : viewInfos) {
			 String content = vo.getContents();
			 String newlineAdapt = content.replaceAll("\r\n", "<br/>");
			 vo.setContents(newlineAdapt);
		 }
		 
		 boardService.updateHit(no);
		 model.addAttribute("viewInfos",viewInfos);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(@AuthUser UserVo authUser, Model model) {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/writesuccess", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo vo) {
		
		String title = vo.getTitle();
		String content = vo.getContents();
		Long userNo = authUser.getNo();
		
		System.out.println(authUser.getName());
		if("".equals(title) || "".equals(content)) {
			return "redirect:/board/write";
		}
		
		int maxGNo = boardService.findMaxGroupNo();
		
		BoardVo newVo = new BoardVo();
		newVo.setTitle(title);
		newVo.setContents(content);
		newVo.setGroupNo(maxGNo+1);
		newVo.setOrderNo(0);
		newVo.setDepth(0);
		newVo.setUserNo(userNo);
		newVo.setUserName(authUser.getName());
		
		System.out.println(newVo);
		boardService.writeList(vo);
		return "redirect:/board";
	}
	
	
	
	
	@RequestMapping(value="/delete/{groupNo}", method=RequestMethod.GET)
	public String delete(@PathVariable("groupNo") int groupNo, @AuthUser UserVo authUser, Model model) {
		
		boardService.deleteList(groupNo);
		return "redirect:/board";
	}
	
	
	
	
}
