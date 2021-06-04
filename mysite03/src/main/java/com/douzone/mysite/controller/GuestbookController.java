package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		
		model.addAttribute("list",list);
		return "guestbook/index";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/deleteform/{no}", method=RequestMethod.GET)
	public String deleteform(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/deleteform";
	}
	
//	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
//	public String delete(@PathVariable("no") Long no
//						,@RequestParam(value="password", required=true, defaultValue="") String password) {
//		guestbookService.deleteMessage(no, password);
//		return "redirect:/guestbook";
//	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(GuestbookVo vo) {
		guestbookService.deleteMessage(vo);
		return "redirect:/guestbook";
	}
	
	
//	@ExceptionHandler(Exception.class)
//	public String handlerException() {
//		// 1. logging 
//		return "error/exception"; //2. forwarding to Sorry~ pages
//		
//		
//	}
	
	
}
