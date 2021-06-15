package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:com/douzone/mysite/config/app/jdbc.properties")
public class DBConfig {
	@Autowired
	private Environment env;
	
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
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));
		return dataSource;
	}
	// 반드시 !!!
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
}
