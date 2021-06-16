package com.douzone.mysite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private GalleryService galleryService;

	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = new ArrayList<>();
		list = galleryService.findAllUrl();
		
		model.addAttribute("urls", list);
		return "gallery/index";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(
			@RequestParam("file") MultipartFile file,
			@RequestParam(value="comment", required=true, defaultValue="") String comment
			) {
		//	/images/파일명 반환. 
		String url = fileUploadService.restore(file);
		if("".equals(url)){
			return "redirect:/gallery";
		}
		GalleryVo vo = new GalleryVo();
		vo.setComment(comment);
		vo.setUrl(url);
		galleryService.uploadImage(vo);
		
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") int no) {
		galleryService.deleteImage(no);
		return "redirect:/gallery";
	}
}
