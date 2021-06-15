```

[src]
  |--- [main]
          |--- [java]
          |       |--- com
          |              |--- douzone
          |                      |--- config
          |                      |      |--- app
          |                      |      |     |--- DBConfig.java	[]
          |                      |      |     |--- MyBatisConfig.java	[]
          |                      |      |--- web
          |                      |      |     |--- MvcConfig.java		[o]
          |                      |      |     |--- SecurityConfig.java	[o]
          |                      |      |     |--- MessageConfig.java	[o]
          |                      |      |     |--- FileuploadConfig.java	[o]
          |                      |--- mysite
          |                             |--- controller
          |                             |--- service
          |                             |--- repository
          |                             |--- vo
          |                             |--- exception
          |                             |--- aop
          |                             |--- confing
          |                                     |--- AppConfig.java	[]
          |                                     |--- WebConfig.java	[o]
          |--- [resources]
          |       |--- logback.xml     		
          |       |--- com
          |              |--- douzone
          |                      |--- mysite
          |                             |--- config
          |                                     |--- app
          |                                     |     |--- jdbc.properties
          |                                     |     |--- mybatis
          |                                     |            |--- configuration.xml
          |                                     |            |--- mappers
          |                                     |                   |--- user.xml
          |                                     |                   |--- board.xml
          |                                     |--- web
          |                                           |--- message_ko.properties
          |                                           |--- fileupload.properties
--company config
src/main/java

com.douzone.config.app	[]
com.douzone.config.web	[o]
com.douzone.mysite.config [o]

--my config
src/main/resources

com.douzone.mysite.config.app	[]
com.douzone.mysite.config.app.mybatis	[]
com.douzone.mysite.config.app.mybatis.mappers	[]
com.douzone.mysite.config.web	[o]
````