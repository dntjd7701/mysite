package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value = {"","/{p}"}, method = RequestMethod.GET)
	public String index(
			@PathVariable(name="p", required=false) Integer p, 
			Model model) {
		
		
		HashMap<String, Integer> map = boardService.paging(p);
		List<BoardVo> list = boardService.getList(map);

		model.addAttribute("lists", list);
		model.addAttribute("map", map);
		return "board/list";
	}
	
	
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model) {
		 List<BoardVo> viewInfos = boardService.viewList(no);
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
		
		
		boardService.writeList(newVo);
		return "redirect:/board";
	}
	
	
	@Auth
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable(value="no") Long no, @AuthUser UserVo authUser) {
		
		
		boardService.deleteList(no);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply(@PathVariable(value="no", required=false) Long no, Model model) {
		model.addAttribute(no);
		return "board/reply";
	}
	
	@Auth
	@RequestMapping(value="/submitreply/{no}", method=RequestMethod.POST)
	public String reply(@PathVariable("no") Long no,
						@RequestParam(value="title", required=true, defaultValue="") String title,
						@RequestParam(value="contents", required=true, defaultValue="") String contents,
						@AuthUser UserVo authUser) {
		
		BoardVo vo = boardService.findByID(no);
		
		BoardVo replyVo = new BoardVo();
		replyVo.setUserNo(authUser.getNo());
		replyVo.setTitle(title);
		replyVo.setContents(contents);
		replyVo.setGroupNo(vo.getGroupNo());
		replyVo.setOrderNo(vo.getOrderNo()+1);
		replyVo.setDepth(vo.getDepth()+1);
	
		boardService.upNo(replyVo);
		boardService.doReply(replyVo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@PathVariable(value="no", required=false) Long no, Model model) {
		List<BoardVo> viewInfos = boardService.viewList(no);
		 for(BoardVo vo : viewInfos) {
			 String content = vo.getContents();
			 String newlineAdapt = content.replaceAll("<br/>", "\\r\\n");
			 vo.setContents(newlineAdapt);
		 }
		model.addAttribute("viewInfos", viewInfos);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/sumitmodify", method=RequestMethod.POST)
	public String modify(
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="contents", required=true, defaultValue="") String contents,
			@RequestParam(value="no", required=true, defaultValue="") Long no) {
	
		boardService.updateView(no, title, contents);
		return "redirect:/board";
	}
	
//	@RequestMapping("/search")
//	public String search(
//			@RequestParam("kwd") String kwd,
//			@RequestParam("search") String search,
//			Model model) {
//		
//		return "board/list";
//	}
}
