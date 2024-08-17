# About SpringBoot Security
- First check about [Spring Security](https://github.com/codophilic/LearnSpring/blob/main/Spring%20Security.md)

## SpringBoot Security vs Spring Security

- **Spring Security** handles authentication (verifying who you are) and authorization (what you are allowed to do) for your application. It provides tools and configurations to secure your application, such as login mechanisms, role-based access control, and protection against common vulnerabilities.
- **Spring Boot Security** provides default configurations and auto-setup for security using Spring Security. It simplifies the process of securing your application by automatically setting up basic security features, so you don’t have to manually configure everything from scratch. When you create a new Spring Boot application and add Spring Security, it automatically sets up basic security (like requiring a login) without you having to manually configure every detail.
- **Spring Security**: Requires manual setup and configuration of security features. You’ll write and configure security-related code yourself.
- **Spring Boot Security**: Automatically configures security settings for you based on default settings and conventions. You can still customize it further if needed.

- Lets create a simple web springboot bank based project

```
package com.springboot.security.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class BankController {

	@GetMapping("/welcome")
	public String getMethodName() {
		return "welcome to the banking application";
	}
	
}
```

![alt text](image.png)

- Now lets add Spring Security dependencies in pom file.

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
```

- Lets again access `/welcome` url.

![alt text](image-1.png)

- Thats weird , we have not created any login page ? how did it appear?, all magic of Spring Boot Security.
- If you add Spring Security to your project without any customization, Spring Boot automatically generates a very basic login page at the `/login` URL. This page includes simple username and password fields.
- What is the user name and password?, initially when we added spring boot security dependencies, the default user name is **user** and the password for the user name is generated on the console.

![alt text](image-2.png)

- Use the user name as **user** and enter the password which is generated in console and try to login.

![alt text](image-3.png)

- By default, Spring Security will assume and protect each and every API and MVC path available inside your project.
- Where is the login page located? how the user name and password is being getting generated? lets see
- **Login Page**: 
    - The default login page provided by Spring Security is generated dynamically at runtime by the Spring Security framework. It is not a static HTML file but is part of the internal implementation of Spring Security which is why you can't find it by searching through your project files.
    - The login page is provided by the **DefaultLoginPageGeneratingFilter** class within the Spring Security framework. This filter is responsible for generating the login page when one is not explicitly defined by the application.
    - Under class **generateLoginPageHtml** you will able to find the html piece of code for the login page.

<video controls src="20240817-0344-34.1771385.mp4" title="Title"></video>

- **User name and Password**:
    - User name and Password is present under the class **SecurityProperties**, if you see the user name is fixed which is **user** but the password is getting generated randomly. So whenever you restart your web application, you'll be seeing a different password in the console.

<video controls src="20240817-0348-35.2661921.mp4" title="Title"></video>

- Can we customize the user name and password? , of course, in the **application.properties** you can defined your user name and password, but what will the key in the property file? if you see there is a `@ConfigurationProperties(prefix = "spring.security")` above **SecurityProperties**, by using `spring.security.user.name` and `spring.security.user.password`. 

>[!NOTE]
> - Under the Static User class the variable name **name** and **password** are defined , so we are access using static properties.

- Adding both the properties in **application.properties**

```
spring.security.user.name=${SPRINGBOOT_USERNAME:defaultUserName}
spring.security.user.password=${SPRINGBOOT_PASSWORD:password@1234}
```

- Hold on what is this **SPRINGBOOT_USERNAME** and **SPRINGBOOT_PASSWORD**? it is recommended what instead of provided hardcoded values into your properties file, you create an environment variables which makes your application loosely couple with configurations, if configuration or environment variables are not available it will the default value you provided (e.g defaultUserName,password@1234)
- Lets create a system environment variable **SPRINGBOOT_USERNAME**, we won't create **SPRINGBOOT_PASSWORD** and use default password which is `password@1234`. You need to configured that new system environment variable into eclipse and then run it as configuration.


<video controls src="20240817-0518-06.8202911.mp4" title="Title"></video>

- If still facing issue with enviroment variables you can add it in the **Run As -> Run Configuration -> Environment -> Select -> (select the variable name)**.

![alt text](image-4.png)

>[!NOTE]
> - If any environment variable is created/deleted or update you need to stop start you eclipse/STS IDE.

- What if it try to login with another tab within the browser? or try to refresh the same page ?

<video controls src="20240817-0333-02.9513369.mp4" title="Title"></video>

- Behind the scenes Spring boot security also manages sessions automatically.

