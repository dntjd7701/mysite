package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.vo.SiteVo;


@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private FileUploadService fileUploadService;
	

	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = adminService.findMain();
		model.addAttribute("siteVo", vo);
		
		return "admin/main";
	}
	
	@ApplicationScope
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(
			SiteVo vo,
			Model model,
			@RequestParam("file1") MultipartFile file1) {
		
		String url = fileUploadService.restore(file1);
		vo.setProfile(url);
		adminService.updateMain(vo);
		model.addAttribute("siteVo",vo);
		return "redirect:/admin";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}

}
