package com.douzone.config.web;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * 	MVC 기본 설정
 *  spring-servlet.xml에서 대체하기 
 * 
 * @EnableWebMvc
 * 	<mvc:annotation-driven>
 * 
 * 
 * 
 * 
 * @author kang-woosung
 */

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	/**
	 *  View Resolver
	 *  
	  	<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
			<property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
			<property name="prefix" value="/WEB-INF/views/"/>
			<property name="suffix" value=".jsp"/>
		</bean>
	 *  
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setExposeContextBeansAsAttributes(true);
		
		return viewResolver;
	}
	
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
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
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

	
	/**
	 * 
	 * <!-- 서블릿 컨테이너의 디폴트 서블릿 위임 핸들러 -->
		<mvc:default-servlet-handler/>
	 * 
	 */
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
}
