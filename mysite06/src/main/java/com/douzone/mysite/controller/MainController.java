package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private ServletContext application;
	
	@RequestMapping("")
	public String index(Model model) {
		SiteVo vo = adminService.findMain();
		model.addAttribute("siteVo", vo);
		application.setAttribute("title", vo.getTitle());
		return "main/index";
	}
	
	
	// String 
	@ResponseBody
	@RequestMapping ("/msg1")
	public String message1() {
		return "안녕 ~";
	}
	
	// Object
	@ResponseBody
	@RequestMapping ("/msg2")
	public Object message2() {
		
		UserVo vo = new UserVo();
		vo.setName("elsa");
		vo.setEmail("elsa@gmail.com");
		
		return vo;
	}
}
