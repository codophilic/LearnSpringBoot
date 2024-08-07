  <img align="center" alt="image"  src="https://github.com/user-attachments/assets/7ee7156e-8164-4fcd-a35b-4b4674efb43d"/>


# About SpringBoot
- Spring Framework is powerful, but it can be complex and requires a lot of boilerplate code and configuration to set up a project. Spring Boot was introduced to simplify this process.
- Spring Boot is built on top of the conventional spring framework. So, it provides all the features of spring and is easier to use than spring.
- Lets see what SprintBoot can do which Spring can't

1. Tedious Configuration
   - **Spring**: Requires extensive XML or Java-based configuration.

```
Downloading dependencies
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.9</version>
</dependency>
<!-- Other dependencies like Spring MVC, AOP, etc. -->

Configuring web.xml
<web-app>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring/dispatcher-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>

Setting up dispatcher configuration (in case of MVC)
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".jsp" />
    </bean>
    <!-- More beans and configurations -->
</beans>
```

   - **Spring Boot**: Uses auto-configuration to set up defaults automatically.

```
Downloading the dependencies
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

Get started with logic
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```


2. External Server
  - **Spring:** In spring you when we are building a web application, we require a server and on the server you will run a **war** file. So you need to configure the server like tomcat then you need to deploy your war file on it.
  - **SpringBoot:** In springboot you create a **jar** file , but jar file cannot be run on server, so springboot provides and embedded server apache tomcat. So you don't need to configure server configuration it is already handle by springboot.

3. Database configurations
  - **Spring:** In spring you need to perform manually do database configurations like transaction manager and providing details of database

```
Downloading dependencies
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
    <version>2.5.5</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.4.32.Final</version>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.200</version>
</dependency>

Database Configuration
@Configuration
@EnableJpaRepositories(basePackages = "com.example.repository")
@EnableTransactionManagement
public class PersistenceConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.example.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        return emf;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
```

  - **SpringBoot:** In spring boot just define application properties with db details and download the dependencies.

```
Downloading dependencies
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>

Application Properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

Start writing logics
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

- Similarly we can do for logging, securities and for other types of configurations where alot of manual tedious configuration which is require when we use spring, is eliminated by springboot. 

## Disadvantage of SpringBoot
- Spring Boot is an opinionated framework. It suggests specific ways to structure your application, handle dependencies, and configure settings. This means you don't have to make as many decisions, and you can get started quickly. However, it also means you might have less flexibility to customize things exactly as you want.
- The auto-configuration can sometimes hide complexities, making it harder to understand what’s happening behind the scenes. This can be problematic when troubleshooting or needing custom configurations.
- The embedded servers and additional layers can increase the memory footprint and disk usage compared to a traditional Spring application that might use a lighter server or more fine-tuned configurations.
- Spring Boot's starter dependencies bundle multiple libraries together, which might include some you don't need, leading to larger application sizes and potentially unused dependencies.
- The embedded server and bundled dependencies might increase memory consumption.
- If your application requires highly customized configurations that deviate significantly from the defaults provided by Spring Boot, achieving this can sometimes be more complex than with a traditional Spring setup.

- While Spring Boot is designed to streamline development and reduce boilerplate, it’s important to consider these potential disadvantages in the context of your specific project requirements. In scenarios where fine-grained control, resource optimization, or legacy compatibility are critical, traditional Spring might offer advantages despite the additional setup complexity.


















































