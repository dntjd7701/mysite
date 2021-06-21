package com.douzone.mysite.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.mysite.security.AuthInterceptor;
import com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver;
import com.douzone.mysite.security.LoginInterceptor;
import com.douzone.mysite.security.LogoutInterceptor;

@SpringBootConfiguration
@PropertySource("classpath:com/douzone/mysite/config/Webconfig.properties")
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private Environment env;
	
//	SecurityConfig
	
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
			.addPathPatterns(env.getProperty("security.auth-url"));
		
		registry
			.addInterceptor(logoutInterceptor())
			.addPathPatterns("security.logout");
		
		registry
			.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("security.auth-url")
			.excludePathPatterns("security.logout")
			.excludePathPatterns("/assets/**");
	}
	
//	MessageConvertor
	
	/**
	 * 	Message Converter
	 * 
	 * <!-- Message Converter -->
		<mvc:message-converters>
		1.
		****************************************************
			<bean class="org.springframework.http.converter.StringHttpMessageConverter">
			    <property name="supportedMediaTypes">
			        <list>
			             <value>text/html; charset=UTF-8</value>
			        </list>
			    </property>
			</bean>
		****************************************************	
			<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
			   <property name="supportedMediaTypes">
			       <list>
			            <value>application/json; charset=UTF-8</value>
			        </list>
			   </property>
			</bean>
		</mvc:message-converters>
	
		<mvc:argument-resolvers>
			<bean class="com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver"/>
		</mvc:argument-resolvers>	
	 * 
	 */
	
	
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter= new StringHttpMessageConverter();
		stringHttpMessageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("text", "html", Charset.forName("utf-8"))
						));
		
		return stringHttpMessageConverter;
	}	
	
	/**
	 * 	Message Converter
	 * 
	 * <!-- Message Converter -->
	 * 	
				<mvc:message-converters>
		****************************************************
		1.
		****************************************************
		2.	<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
			   <property name="supportedMediaTypes">
			       <list>
			            <value>application/json; charset=UTF-8</value>
			        </list>
			   </property>
			</bean>
		*****************************************************
				</mvc:message-converters>
		<mvc:argument-resolvers>
			<bean class="com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver"/>
		</mvc:argument-resolvers>	
	 * 
	 * 
	 */
	
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		new Jackson2ObjectMapperBuilder()
				.indentOutput(true)
				.simpleDateFormat("yyyy-mm-dd");
		
		
		
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = 
				new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("application", "json", Charset.forName("utf-8"))
						));
		
		return mappingJackson2HttpMessageConverter;
	}

	
	/**
	 * 	Message Converter
	 * 
	 * <!-- Message Converter -->
	 * 	****************************************************
	 * 	3.
				<mvc:message-converters>
		****************************************************
		1.
		****************************************************
		2.	
		*****************************************************
		3. 
				</mvc:message-converters>
	 	****************************************************
		<mvc:argument-resolvers>
			<bean class="com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver"/>
		</mvc:argument-resolvers>	
	 * 
	 * 
	 *  !! extends WebMvcConfigurerAdapter
	 *  override
	 *  
	 *   등록하는것. 
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}

//	FileuploadConfig
	/**
	 *  Resource Mapping(URL Magic Mapping)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
				.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation"));
	}
	
	
}
