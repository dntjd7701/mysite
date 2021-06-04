package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception e) {
		// 1. logging
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		
		
		System.out.println(errors);
		// 2. sorry~ page
		
		
		// 3. 정상 종료 
		
		model.addAttribute("exception", errors.toString());
		
		return "error/exception";
	}
}
