# About SpringBoot Security
- First check about [Spring Security](https://github.com/codophilic/LearnSpring/blob/main/Spring%20Security.md)

# Need of Security

![alt text](image-5.png)

![alt text](image-6.png)

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

## Spring Security Internal flow

![alt text](image-7.png)

- Now, let's try to understand the internal flow of Spring Security Framework.
### Step 1

#### End User Request
- In the very first step, some client application, like a browser or it can be a mobile application or it can be a postman,these end users will going to send a request to access our REST API or a MVC path. If they're trying to access a protected API, obviously, the end user, they have to provide their credentials as part of the Request Header or Request Body based upon the configurations
- If the user is trying to access a protected API without mentioning credentials, we know what is going to happen. The Spring Security Framework, it is going to redirect the user to the login page to enter his or her credentials.
- So this job of identifying, if the user is authenticated or not is going to be done by the Spring Security filters.

#### Spring Security Filters

- In Servlets, **filters,  an important role in intercepting each and every request**. Inside the Spring Security Framework also, there are more than 20 different filters intercepting each and every request and handling the scenario specific to them.
- For example, there is a filter inside the Spring Security Framework to identify, if the user is authenticated or not. If the user is not authenticated, the responsibility of that filter is to redirect to the login page. And similarly, there is a filter to detect the CSRF attack.
- As soon as a client send a request to the backend API, which is being secured by a Spring Security Framework, the request is going to be intercepted by the Spring Security filters. Based upon your configurations, 5 or 10 or 15 or 20 filters, they're going to process your request one by one. At any layer, if there is an error, then the end user will get an 401 error or 403 error based upon the exception type.

### Step 2

#### Authentication Object

- If end user provided the proper credentials inside the request when he's trying to access a secured API, the Spring Security filters will populate the **authentication object**.
- The credentials that we receive from the end user, they are going to be in the HTTP request object since we can't forward the same HTTP servlet request object throughout the framework code and throughout the business logic. The very first responsibility of the Spring Security filters is going to be convert the credentials from the HTTP servlet request object to authentication object.

```
Authentication Object
User name="Entered by End User"
User Password="Entered by End User"
IsAuthenticated=false
```

- Inside this authentication object, there'll be fields like username, password and there is also a boolean to identify whether the user is authenticated or not. So initially when these object is created very first time, this `isAuthenticated` boolean value is going to be set as false.
- Apart from that, the username and password, they're also going to be populated like **HarshPandya** is a username in our case and password is going to be **password@1234**. The same authentication object is going to be used by all other components. You can assume this authentication object is a common contract which can be understand by all the components.

### Step 3

#### Authentication Manager

- Inside the Spring Security Framework these authentication object represents an user credentials and it also has an information to identify if the user is authenticated or not. So once the authentication object is populated, the filter will forward the request to the authentication manager.
- This **authentication manager is going to take the responsibility of completing the authentication and conveying the results back to the filters whether the authentication is successful or not**. So this authentication manager will say, "Hey filters, don't worry. You give me the authentication object with the username, password, I'm going to take the responsibility of authenticating the user by taking help from other components (Authentication providers). Once all authentication logic is executed, I'm going to convey you the results."
- The **authentication manager** is an interface.

>[!NOTE]
> - Authentication manager, it is only going to take the responsibility of completing the authentication, but it is not going to do the actual authentication. So in order to perform the actual authentication, this authentication manager, it is going to forward the request to the authentication providers available inside the Spring Security Framework.
> - We can also define our own authentication providers. So a project can have more than one authentication provider.

### Step 4,5,6

#### Authentication Providers

- So the responsibility of Authentication Manager is to check with all the applicable authentication providers to identify whether the authentication is successful or failed. So once these authentication providers receive the authentication object to perform the actual authentication, these authentication providers, they're going to take help from other components **UserDetailsManager/UserDetailsService** and **Password Encoder**.

##### User Details Manager / User Details Service

- The authentication provider loads the authentication object with the help of UserDetailsManager or UserDetailsService implementation classes.
- So once the user details are loaded, the user details will be sent back to the authentication provider. But authentication provider, it won't compare the password provided by the user and what is present inside the storage system like database or inside the memory. It can't do the password comparison by itself. It has to take the help from another component which is **Password Encoder**. 

##### Password Encoder

- So this Password Encoder component is a specialist while dealing with the password comparison. Using these Password Encoder, we can compare the password using plain text or we can store them using the hashing algorithms.
- So since these Password Encoder is a specialist, the authentication provider will ask the Password Encoder to compare the user provided password with the password loaded from the storage system with the help of **UserDetailsManager**. Once the Password Encoder says that, okay, the passwords are matching accordingly, the Authentication Provider conveys back to the authentication manager saying that the authentication is successful.

### Step 7,8

- So how it is going to convey that? this time inside the response, the authentication object, it is going to have the boolean value, which is **isAuthenticated as true**. So based upon this value, authentication manager know whether the authentication is successful or not.
- The same authentication object will send back to the Spring Security filters. So now Spring Security filters, they know whether the authentication is successful or not. Regardless of whether the authentication is successful or not, they're going to store the authentication details in a security context.

### Step 9

#### Security Context

- So Spring Security filters will store these authentication object against a session ID which is created for a given browser. So if the user is trying to access the same protected page from the same browser based upon the given session ID the Spring Security filters, they're going to load the authentication object details from the security context and accordingly, they're going to show either successful message or a error message. So this should also give a confirmation to you qll the actual authentication execution is only going to happen for the very first request because there is no entry for a given session ID inside the security context.
- Once the first request is processed and there is an entry inside the security context for session ID from next time onwards, the security filters, they're not going to invoke the authentication manager. Instead they're going to leverage the details present inside the security context to send the successful response or to send an error response.

### Step 10

- At last, in the step 10, we are going to send a response to the client application.