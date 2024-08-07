# About SpringBoot MVC
- When we create MVC structure in spring a lot of configuration is required example configuring dispatcher servlet, View Resolver and etc. Check here about [Spring MVC](https://github.com/codophilic/LearnSpring/blob/main/Spring%20MVC.md)
- Lets see how easier we can do MVC structure in SpringBoot. Lets create a simple web project in STS

![alt text](image.png) 

- Select Spring web as the required dependency

![alt text](image-1.png)

- Click on finish , if you see the dependencies are automatically added in the **pom.xml** file

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
```

- Below is the main method 

```
package com.spring.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```
- Lets create controller package and add a MainController class which will be our FrontController

```
package com.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/welcome")
	public String welcomePage() {
		return "welcome";
	}
}
```

- We need to create welcome.jsp file, to create that we need to create manually `webapp/pages` folder under `main`

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Welcome Page</h1>
</body>
</html>
```

- Post execution of main method , we can see the page. To run the project use **Spring Boot App**

```
package com.spring.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		
		//IoC Container
		SpringApplication.run(DemoApplication.class, args);
	}

}
```

![alt text](image-2.png)

- Wait where is the welcome page? , uptil now we have defined jsp file and controller class but we have not said springboot that all my pages are under **pages** folder with extension as **.jsp**.
- So to do that we have **application.properties** file, where we specify suffix and prefix to resolve the view for ViewResolver.

![alt text](image-3.png) 

- If you see STS provides suggestion while performing configuration in application.properties

```
application.properties

spring.application.name=demo
spring.mvc.view.prefix=/pages/
spring.mvc.view.suffix=.jsp
```

- Now if we restart and check again we will get the same page, now what is the issue? if you observe below image when we try to hit the url `http://localhost:8080/welcome` , we can see a file getting downloaded.

![alt text](image-4.png)

![alt text](image-5.png) 

- Spring Boot includes an embedded Tomcat server by default for serving web applications. However, it does not include JSP support out of the box because the embedded Tomcat does not include the Jasper JSP engine necessary for processing JSP files.
- By adding the `tomcat-embed-jasper` dependency, you include the Jasper engine, which enables JSP support. The embedded Tomcat server can then process and render JSP files.

```
<!-- Tomcat Embed Jasper for JSP support -->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
    <scope>provided</scope>
</dependency>
```

- Post running the main method, we get the page


![alt text](image-6.png) 

- Currently the project is running on tomcat port number 8080, lets say if we want to change the port , we can do this in **application.properties**

![alt text](image-7.png)

- Configuring port 8081 temporarily

![alt text](image-8.png)

![alt text](image-9.png) 

- Currently we are running this web springboot project in java perspective in STS, there is no server added and also not open in Java EE perspective.

![alt text](image-10.png)

## What is application.properties?

- In a Spring Boot application, **application.properties** is a file used to configure various settings for your application. It is typically located in the `src/main/resources` directory. This file allows you to set key-value pairs that configure different aspects of your application, such as database settings, server ports, logging levels, customize any configuration and more.
- Spring Boot automatically loads and reads the **application.properties** file at startup. It uses the properties defined in this file to configure various parts of the application. The framework has built-in mechanisms to look for this file in the `src/main/resources` directory or on the classpath.
- Discover all the properties [here](https://docs.spring.io/spring-boot/appendix/application-properties/index.html)
- **application.properties** is a central place for configuring your Spring Boot application. Spring Boot automatically reads this file at startup and uses the properties defined within it to configure various components of your application. The flexibility and simplicity of application.properties make it a powerful tool for managing your application's configuration.






























