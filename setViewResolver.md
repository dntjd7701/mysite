


<!--ViewResolver 설정 --> 
	<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceView">
		<property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
		<property name="prefix" value="/WEB-INF/views"/>
		<property name="suffix" value=".jsp"/>
	</bean>