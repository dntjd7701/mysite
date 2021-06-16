package com.douzone.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:com/douzone/mysite/config/web/fileupload.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter{
	@Autowired
	private Environment env;
	
	/**
	 * <!-- Multipart Resolver  -->
			<!-- 멀티파트 리졸버 -->
		<bean id="multipartResolver" 
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		
			<!-- 최대업로드 가능한 바이트크기 -->
		<property name="maxUploadSize" value="52428800" />
			<!-- 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기 -->
			<!-- property name="maxInMemorySize" value="52428800" /-->
			
			<!-- defaultEncoding -->
		<property name="defaultEncoding" value="utf-8" />
	</bean>	
	 */
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		
		multipartResolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize", Long.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding"));
		
		return multipartResolver;
	}

	/**
		<!--mvc resources mapping  -->
		<!-- mapping 시에 img들의 위치는 location과 mapping이 각각 지정하는곳에 있어야한다.   -->
		<mvc:resources location="file:/Users/kang-woosung/uploads-mysite/" mapping="/images/**"/>
	 */
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
				.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation"));
	}
	
	
}
