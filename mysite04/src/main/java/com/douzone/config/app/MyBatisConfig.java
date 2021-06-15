package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

	/**
	 	<!-- MyBatis SqlSessionFactoryBean --> 
		<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
			<property name="dataSource" ref="dataSource" /> 
			<property name="configLocation" value="classpath:mybatis/configuration.xml" /> 
		</bean>
	 */
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory= new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:com/douzone/mysite/config/app/mybatis/configuration.xml"));
		return sqlSessionFactory.getObject();
	}
	
	/**
	 * <!-- MyBatis SqlSessionTemplate --> 
		<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
			<constructor-arg index="0" ref="sqlSessionFactory" />
		</bean>
	 */
	
	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
