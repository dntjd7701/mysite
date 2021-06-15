package com.douzone.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
	
	/**
	 * <!-- 6. MessageSource -->
		<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
		   <property name="basenames">
		      <list>
				<value>messages/messages_ko</value>
				<!-- <value>messages/messages_jp</value>
				<value>messages/messages_cn</value> -->
		      </list>
		   </property>
		</bean>
	 * 
	 */
	
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("com/douzone/mysite/config/web/messages_ko");
		messageSource.setDefaultEncoding("utf-8");
		
		return messageSource;
	}
	
}
