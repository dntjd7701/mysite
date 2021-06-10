package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

/**
 * 얘는 응답을 json으로.
 * @author kang-woosung
 *
 */
//@RestController >> ResponseBody 안써도 다 메시지로. 
//@GetMapping() -- >RequestMapping, get 방식.
@Controller("userControllerApi")
@RequestMapping("/user/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public JsonResult checkEmail(
			@RequestParam(value="email", required=true, defaultValue="") String email) {
		
		UserVo userVo = userService.getUser(email);
		return JsonResult.success(userVo != null);
	}

}
