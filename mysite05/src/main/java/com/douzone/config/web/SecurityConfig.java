package com.douzone.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.douzone.mysite.security.AuthInterceptor;
import com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver;
import com.douzone.mysite.security.LoginInterceptor;
import com.douzone.mysite.security.LogoutInterceptor;


@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter{
	
	/**
	 *  Argument Resolver
	 *  
	 *  <mvc:argument-resolvers>
	 	**********************************
	 	1.
			<bean class="com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver"/>
		**********************************
		</mvc:argument-resolvers>	
	 */
	@Bean
	public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}

	/**
	 *  Argument Resolver
	 *  
	 *  **********************************
	 *  2.
	 *  <mvc:argument-resolvers>
	 	**********************************
	 	1.
		**********************************
		2.
		</mvc:argument-resolvers>	
		**********************************
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(handlerMethodArgumentResolver());
	}
	
	
	/**
	 * 	<!-- Interceptors  -->
		<mvc:interceptors>
			<mvc:interceptor>
			
				<mvc:mapping path="/user/auth"/>
				<bean class="com.douzone.mysite.security.LoginInterceptor"/>
			</mvc:interceptor>
		</mvc:interceptors>
		
		
		<mvc:interceptors>
			<mvc:interceptor>
				<mvc:mapping path="/user/logout"/>
				<bean class="com.douzone.mysite.security.LogoutInterceptor"/>
			</mvc:interceptor>
		</mvc:interceptors>
		
		<!-- 위에 둘 제외 + assets 애들 하고 나머지 다~ 검사   -->
		<mvc:interceptors>
			<mvc:interceptor>
				<mvc:mapping path="/**"/>
				<mvc:exclude-mapping path="/user/auth"/>
				<mvc:exclude-mapping path="/user/logout"/>
				<mvc:exclude-mapping path="/assets/**"/>
				<bean class="com.douzone.mysite.security.AuthInterceptor"/>
			</mvc:interceptor>
		</mvc:interceptors>
		
		1. bean 
		2. interceptor 등록
		3. mapping
		4. exclude
	 */
	
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	@Bean
	public HandlerInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/auth");
		
		registry
			.addInterceptor(logoutInterceptor())
			.addPathPatterns("/user/logout");
		
		registry
			.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/auth")
			.excludePathPatterns("/user/logout")
			.excludePathPatterns("/assets/**");
	}
	
	
}
