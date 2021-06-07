package com.douzone.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 이 annotation을 어디에 붙여야 하는지. 
// 변수, 클래스, 메소드 등 어느 곳에. 
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	
//	public String value() default "USER";
	public String role() default "USER";
	public boolean test() default false;
}
