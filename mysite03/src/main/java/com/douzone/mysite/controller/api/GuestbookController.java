package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@RestController("guestbookControllerApi")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping("/{no}")
	public JsonResult list(@PathVariable Long no) {
		System.out.println(no);
		List<GuestbookVo> list = guestbookService.getMessageList(no);
		return JsonResult.success(list);
	}
	
	@PostMapping("")
	public JsonResult add(@RequestBody GuestbookVo vo) {
		System.out.println(vo);
		guestbookService.addMessage(vo);
		return JsonResult.success(vo);
	}
	
	@DeleteMapping("/{no}")
	public JsonResult delete(
			@PathVariable Long no,
			@RequestParam(value="password", required=true, defaultValue="")String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		boolean result = guestbookService.deleteMessage(vo);
		
		if(result != true) {
			return JsonResult.fail("비밀번호가 틀렸습니다.");
		} else {
			return JsonResult.success(vo);
		}
		
	}
}




