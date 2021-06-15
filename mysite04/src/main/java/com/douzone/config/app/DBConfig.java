package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfig {
	
	/**
	 * <!-- Connection Pool DataSource-->
		<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
			<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
			<property name="url" value="jdbc:mysql://127.0.0.1:3306/webdb?allowPublicKeyRetrieval=true" />
			<property name="username" value="webdb" />
			<property name="password" value="webdb" />
		</bean>
		<!-- characterEncoding=utf8 -->
	 */
	 
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/webdb?allowPublicKeyRetrieval=true");
		dataSource.setUsername("webdb");
		dataSource.setPassword("webdb");
		dataSource.setInitialSize(10);
		dataSource.setMaxActive(100);
		return dataSource;
	}
	// 반드시 !!!
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
}
