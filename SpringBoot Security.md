# About SpringBoot Security
- First check about [Spring Security](https://github.com/codophilic/LearnSpring/blob/main/Spring%20Security.md)

# Need of Security

![alt text](image-5.png)



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

- Inpsect your page and go to application , you will a JSESSION ID

![alt text](image-10.png)

- Open a new tab, and go application you will get the same JSESSION ID

![alt text](image-11.png)

- If we edit the JESSION ID, it will tell us to login again.
- The login page has a JSESSIONID value. Behind the scenes, the Spring Security framework, it is going to remember this JSESSIONID value belongs to an unauthenticated user. That's why as a next step, when we try to log in into the application and once the authentication is successful, we are going to get a new JSESSIONID value. For the same, I'm going to enter the username as HarshPandya followed by password@1234 as a password.
- As soon as I click sign in, you'll be able to see this value is going to be changed. So let me click on the sign in button and you can see, we got a new JSESSIONID value.
- Spring Security will map to a successful authenticated session where the authentication details, they're going to be stored and they'll be reloaded and reused every time when I try to access a protected API.
- Spring Security framework, also doing a smart job of not invoking the actual authentication every time. If you try to invoke the actual authentication for each and every request, then definitely it is going to have some performance impact. But by using this JSESSIONID cookie, the Spring Security framework is doing a smart job here.

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

## Spring Security Internal Flow Implementation

- Before going into the implementation of Spring Security, enabled the logging at trace level by adding below line in **application.properties**

```
logging.level.org.springframework.security=TRACE
```

- This will only trace security related part in spring and log that into the console.
- Lets run the application and see the console.

```
highlighting only selective trace levels..
FilterChainProxy - Trying to match request against DefaultSecurityFilterChain [RequestMatcher=any request, Filters=[org.springframework.security.web.session.DisableEncodeUrlFilter@14832ea7, org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@250bef5f, org.springframework.security.web.context.SecurityContextHolderFilter@434c346d, org.springframework.security.web.header.HeaderWriterFilter@22f05888, org.springframework.web.filter.CorsFilter@3fb5a66a, org.springframework.security.web.csrf.CsrfFilter@2e096e85, org.springframework.security.web.authentication.logout.LogoutFilter@36a979ea, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@76e563da, org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@62ebfbe8, org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter@38af0f76, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@1e4b5b3c, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@44f6d66b, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@21c48b33, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@35d4f8d2, org.springframework.security.web.access.ExceptionTranslationFilter@f16cf1, org.springframework.security.web.access.intercept.AuthorizationFilter@5eae6b99]] (1/1)
FilterChainProxy - Securing GET /login
FilterChainProxy - Invoking DisableEncodeUrlFilter (1/16)
FilterChainProxy - Invoking WebAsyncManagerIntegrationFilter (2/16)
FilterChainProxy - Invoking SecurityContextHolderFilter (3/16)
FilterChainProxy - Invoking HeaderWriterFilter (4/16)
FilterChainProxy - Invoking CorsFilter (5/16)
```

- If you see there are over 16 filters applied when we hit **/welcome** page. Lets focus in **AuthorizationFilter** class. Under that there is a method **doFilter()** which checks if the request is authorize or not, if not then it throws an exception as 'Access Denied' . This results to generate a login page using **DefaultLogoutPageGeneratingFilter**.
- Here **UsernamePasswordAuthenticationFilter** filter is invoked for authentication which extends an abstract class **AbstractAuthenticationProcessingFilter** in the below flow. 

![alt text](image-8.png)

![alt text](image-9.png)

- Lets create a bank based web application, where there would be tab urls for **Accounts, Balance, Cards, Contact, Loans and Notice**.
- Lets create different controller for these.

![alt text](image-12.png)

- By default all the MVC paths are secured by spring boot, it is not always necessary to secure all the paths e.g for contact & notice pages you don't require any security. How to do this? - first let us understand how all MVC path are secured? so under **SpringBootWebSecurityConfiguration** there is a static block which has a method **defaultSecurityFilterChain**

![alt text](image-13.png)

- `@Configuration` annotation indicates that the class defines beans, which are methods annotated with @Bean. These beans will be managed by Spring's application context.
- `proxyBeanMethods = false` attribute is used to optimize performance by not creating proxy beans for method calls within the same class.
	- When set to **true** Spring creates proxies for bean methods. This ensures that calling a `@Bean` method multiple times returns the same bean instance, enabling singleton behavior and dependency injection between beans within the configuration class.
	- When set to **false**, Spring does not create proxies. This means that calling a `@Bean` method multiple times may create multiple instances of the bean.

>[!NOTE]
> - Prototype scope of bean is not same as **`proxyBeanMethods = false`**.
> - When proxyBeanMethods is set to false, calling a method within the configuration class does not reuse the bean managed by Spring. Instead, it acts like a normal method call in Java, creating a new instance each time (like new ClassName()), not managed by Spring.
> - Whereas in prototype the scope of the bean is still managed by Spring

- `@ConditionalOnDefaultWebSecurity` annotation means that this configuration will only be applied if the default Spring Security configuration is in effect (i.e., no custom security configurations have been provided).
- `@Bean` method is marked as a bean, meaning its return value will be registered as a bean in the Spring application context.
- `@Order(SecurityProperties.BASIC_AUTH_ORDER)` specifies the order in which this security filter chain will be applied. The order is defined by **SecurityProperties.BASIC_AUTH_ORDER**, which is a predefined constant in Spring Security.
- `SecurityFilterChain` bean defines the security filter chain, which is a series of filters applied to incoming requests.
- `http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());` accepts all the http request and provides authentication.
- `http.authorizeHttpRequests` configures authorization for HTTP requests. The code specifies that any request must be authenticated (i.e., users must log in).
- `http.formLogin(withDefaults())` enables form-based login with default settings. When users try to access a protected resource, they will be redirected to a login page.
- `http.httpBasic(withDefaults())` enables HTTP Basic authentication with default settings. This is a mechanism that allows a user to authenticate using a username and password sent in the HTTP headers.
- `http.build()` finalizes the configuration and builds the SecurityFilterChain object that will be used to secure the application.
- Now if we want to customize the authentication on MVC paths, we need to customize the **defaultSecurityFilterChain** method.
- So lets create a **ProjectSecurityClass** and add this method.

```
package com.springboot.security.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfiguration {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}
}
```

- Instead of `authenticated()` method lets add `permitAll()` and check what happens

```
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}
```

![alt text](image-14.png)

- `permitAll()` allows end user to access all the pages without asking any logging credentials.

- What happens when we use `denyAll()` ?

```
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}
```

![alt text](image-15.png)

- `denyAll()` allows end user to perfom login but denies end user to access the page even though the user is authorized to access it.
- So now lets secure pages only of `/accounts`, `/balance`, `/cards` & `/loans` and provide permit all to `/contact` & `/notice`

```
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.
				requestMatchers("/accounts","/balance","/cards","/loans").authenticated().
				requestMatchers("/contact","/notice").permitAll()
				);
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}
```

<video controls src="20240817-1845-41.6415042.mp4" title="Title"></video>

- Our require MVC path got secured , but if you see when we try to login on `/welcome` path, we got 403 error which unauthorized. So basically if we don't specify any path under `authenticated` or `permitAll` it will go under `denyAll`.
- If you see the code `http.formLogin(withDefaults());` due to this the `/login` page is invoked, what if we disable it?

```
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.
				requestMatchers("/accounts","/balance","/cards","/loans").authenticated().
				requestMatchers("/contact","/notice").permitAll()
				);
		http.formLogin(i->i.disable());
		http.httpBasic(withDefaults());
		return http.build();
	}
```

![alt text](image-16.png)

- If you disable form login, you will need to provide another mechanism for authentication, such as HTTP Basic authentication, OAuth2, JWT, etc., or your custom login process. Currently if you see `httpBasic` authentication is available , due to which we got this page. This is a simple authentication scheme built into the HTTP protocol. When you enable it in Spring Security, the browser will show a basic authentication dialog box whenever you try to access a protected resource.

>[!NOTE]
> - When we disable form login, **UsernamePasswordAuthenticationFilter** filter class is not invoked instead **BasicAuthenticationFilter** filter class is invoked. Add a debug point in **doFilterInternal()** method of **BasicAuthenticationFilter** class.

- If we disable `httpBasic()` authentication filter then the Spring security will give 403 error.
- Lets try to hit the bank application urls using postman.

![alt text](image-17.png)

- To access and protected page, we need to provide credentials while sending the request via postman. So in the **Auth** you need to select basic authentication and entered user name and password.


<video controls src="20240818-0609-37.0874476.mp4" title="Title"></video>

-  If you see in the headers value are in base64 encoded format. Below when we decode it we get **user_name:password**. You can also see JSESSION ID present in the headers.

![alt text](image-18.png)

- Currently we are authenticate using a single user details? what if there are multiple users ? so there are two ways, either you have a database where you could store all the user details or if spring has some memory which can keep this user details or **InMemoryUserDetailsManager**

>[!WARNING]
> - Incase of development phase it is recommended to use **InMemoryUserDetailsManager** and not in production.

- In the spring security internal flow , we have saw the there is an **UserDetailsService** .

![alt text](image-19.png)

- **UserDetailsService** is an interface which is extended by **UserDetailsManager**. The **UserDetailsManager** is implemented by **InMemoryUserDetailsManager** as well as by **JdbcUserDetailsManager**.

![alt text](image-20.png)

- So to add multiple user we need to create a bean of **UserDetailsService** under our ProjectSecurityConfiguration config class.

```
	@Bean
	public UserDetailsService userDetailsService() {
		
	}
```

- Okay but how do we create users? if we open the UserDetailsService interface we will be able to see **UserDetails** return type.

![alt text](image-21.png)

- So **UserDetails** is an interface which is implemented by **User** class , the **User** class provide some instance variables like username and password.

![alt text](image-22.png)

- So lets create users using User class.

```
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails u1=User.withUsername("user1").password("password1").authorities("read").build();
		UserDetails u2=User.withUsername("user2").password("password2").authorities("admin").build();
		return new InMemoryUserDetailsManager(u1,u2);
	}
```

- **InMemoryUserDetailsManager** is an implementation of **UserDetailsManager** which extends **UserDetailsService**, so inside **InMemoryUserDetailsManager** there is a parameterized constructor which accepts any number of arguments. This will create user and uses map collections to put those new users.
- **InMemoryUserDetailsManager** in Spring Security is a simple implementation of the **UserDetailsService** interface that stores user details in memory. It’s often used for creating a basic set of users for authentication purposes during development.

```
In InMemoryUserDetailsManager class,

	private final Map<String, MutableUserDetails> users = new HashMap<>();

	@Override
	public void createUser(UserDetails user) {
		Assert.isTrue(!userExists(user.getUsername()), "user should not exist");
		this.users.put(user.getUsername().toLowerCase(), new MutableUser(user));
	}

	public InMemoryUserDetailsManager(UserDetails... users) {
		for (UserDetails user : users) {
			createUser(user);
		}
	}
```

- Lets run the application and try to access the page with new users created. Before running comment out the user name which we created in application.properties

```
#spring.security.user.name=${SPRINGBOOT_USERNAME:defaultUserName}
#spring.security.user.password=${SPRINGBOOT_PASSWORD:password@1234}
```

- When we try to login , on the console we can observer it is showing **Access Denied**

![alt text](image-24.png)

- I am entering a wrong password? nope, even after entering write password we get an access denied error , this is because the password we entered are converted into encoded format and during the build time of new user the password is still is in plain text format. So due to mismatch between encoded password from the frontend and plain text password during the build of new user, we get access denied error. Below is the image when the user1 is build and the password is in plain text format.

![alt text](image-25.png)

- When you enter a password on a login page, Spring Security automatically encodes the entered password using the configured `PasswordEncoder`. If you're storing passwords in plain text within InMemoryUserDetailsManager, you need to tell Spring Security not to encode or check the encoding of these passwords. This is where `{noop}` comes into play. `{noop}` is a prefix used to indicate that the password is stored in plain text and should not be encoded.

- So to tell spring that our password should be compare in plain text format we need to use `{noop}`. Update code

```
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails u1=User.withUsername("user1").password("{noop}password1").authorities("read").build();
		UserDetails u2=User.withUsername("user2").password("{noop}password2").authorities("admin").build();
		return new InMemoryUserDetailsManager(u1,u2);
	}
```

- Using new user details we were able to login

![alt text](image-23.png)

- Using `{noop}` is not a good choice . It should be always in hashed or encrypted format. So spring security provides some default hashing and encrypted method under interface **PasswordEncoder**

![alt text](image-26.png)

- The interface **PasswordEncoder** is implemented by multiple class , lets checkout **PasswordEncoderFactories**

![alt text](image-27.png)

- There are multiple encoding format which you can prefer but most of them are deprecated. If you see the , the default password encryption provided by Spring security is **Bcrypty**.
- Lets implement this in our ProjectSecurityConfiguration class.

```
package com.springboot.security.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfiguration {

	/**
	 * Customize Spring Securities
	 * - permitAll() -> Allows end user to access all the pages without asking any logging credentials
	 * - denyAll() -> Allows end user to perfom login but denies end user to access the page even though the user is authorized to access it.
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.
				requestMatchers("/accounts","/balance","/cards","/loans").authenticated().
				requestMatchers("/contact","/notice").permitAll()
				);
		http.formLogin(withDefaults());
		//http.formLogin(i->i.disable());
		http.httpBasic(withDefaults());
		//http.httpBasic(i->i.disable());
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails u1=User.withUsername("user1").password("password1").authorities("read").build();
		UserDetails u2=User.withUsername("user2").password("password2").authorities("admin").build();
		return new InMemoryUserDetailsManager(u1,u2);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
```

- If you see we have removed the prefix `{noop}` the default PasswordEncoder will be used here is **Bcrypt**. Once the password is hashed it cannot be converted into original password again and the comparision of the password entered from the login screen and in memory will be based on hash comparision.

>[!IMPORTANT]
> - Another way to write return Bcrypt password encoder is **`return new BCryptPasswordEncoder()`**.
> - It is always recommended that to use **`PasswordEncoderFactories.createDelegatingPasswordEncoder()`** because todays Bcrypt encoding is suggested by spring security, but in future if any more powerful encoding algorithm is created it might be the default encoding recommendation by spring security.


- Now since we are using Bcrypt as our password encoder, we need to convert our `password1` and `password2` into Bcrypt format and store it in the **UserDetailsService** method. You can convert the pass refering [this](https://bcrypt-generator.com/) website.
- Updating the UserDetailsService code.

```
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails u1=User.withUsername("user1").password("{bcrypt}$2a$12$y/1h97NMjduYYwnbddNy0eyT0wXax.x9QTsfzor5HA4xzrIVM9Ss.").authorities("read").build();
		UserDetails u2=User.withUsername("user2").password("{bcrypt}$2a$12$u6EKvxZR6kcLeZh4pIS9ue2FrKEsqyz7v1FQaakCuU4PLiA3KaIXG").authorities("admin").build();
		return new InMemoryUserDetailsManager(u1,u2);
	}
```

- Here we mentioned prefix `{bcrypt}` saying spring to treat out password in bcrypt format. Post running we will be able to logged in into our application.

![alt text](image-28.png)

- We can have encoding format vary based on different user like below.

```
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails u1=User.withUsername("user1").password("{noop}password1").authorities("read").build();
		UserDetails u2=User.withUsername("user2").password("{bcrypt}$2a$12$u6EKvxZR6kcLeZh4pIS9ue2FrKEsqyz7v1FQaakCuU4PLiA3KaIXG").authorities("admin").build();
		return new InMemoryUserDetailsManager(u1,u2);
	}
```

- What if your password gets compromised? using simple password can lead hackers to break it easily even after using strong password encoders. Spring security prevents users to login in using simple password. Lets check now.
- Under ProjectSecurityConfiguration we need to add a new bean 

```
	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
```

- Lets dive into **CompromisedPasswordChecker**, so **CompromisedPasswordChecker** is an interface which is implemented by **HaveIBeenPwnedRestApiPasswordChecker()**. Below is the code for **HaveIBeenPwnedRestApiPasswordChecker()**

![alt text](image-29.png)

- If you see it uses an api url `https://api.pwnedpasswords.com/range/` .  [This](https://haveibeenpwned.com/API/v3) is an open source api which helps to check if a password could be compromise or not. So using this api, to check whether a password could be compromise or not , spring security provides **HaveIBeenPwnedRestApiPasswordChecker** class.

- Lets run the application and try to use our existing user1 password which is `password1`.

![alt text](image-30.png)

- So lets change the password for user1 to `thisCouldBe@1234` and user2 to `thisCoundNotBe@1234` (use the bcrypt generated password for it) and re run the application.

```
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails u1=User.withUsername("user1").password("{noop}thisCouldBe@1234").authorities("read").build();
		UserDetails u2=User.withUsername("user2").password("{bcrypt}$2a$12$ThHcCo7ZvUJ4QjCsaoy87e74mwzP5fhzWA4MxDwaNOU0bxqoOv.Aa").authorities("admin").build();
		return new InMemoryUserDetailsManager(u1,u2);
	}
```

![alt text](image-31.png)

- The HaveIBeenPwnedRestApiPasswordChecker is a component that integrates with the "Have I Been Pwned" (HIBP) API to check if a password has been exposed in known data breaches.

<details>
<summary> What is "Have I Been Pwned"? </summary>

```
HIBP is a service created by security expert Troy Hunt that allows users to check if their personal information (like email addresses or passwords) has been compromised in data breaches.
It provides an API that developers can use to check whether a specific password has appeared in any known breaches.
```
</details>

>[!NOTE]
> - This security measure is been introduce for spring security 6.3+

### Why UserDetailsService is required when we have UserDetailsManager?

- **UserDetailsService** is an interface which is implemented by **UserDetailsManager**. 
- The UserDetailsService interface is designed specifically for retrieving user-related data. It has a single method, loadUserByUsername(String username), which is responsible for loading user details (like username, password, roles) by the username. The return type is **UserDetails**.
- UserDetailsService isolates the responsibility of loading user details, making it easier to customize or implement different retrieval mechanisms (e.g., from a database, LDAP, or in-memory).
- UserDetailsManager extends UserDetailsService and adds methods for managing user details, such as creating, updating, or deleting users. It includes methods like createUser(UserDetails user), updateUser(UserDetails user), deleteUser(String username), and others.
- While UserDetailsService is focused on retrieval, UserDetailsManager adds the ability to manage users, which is a broader responsibility. This includes CRUD (Create, Read, Update, Delete) operations on user data.
- By separating the retrieval (UserDetailsService) and management (UserDetailsManager) concerns, Spring Security allows for more modular and flexible designs. You can implement just UserDetailsService if you only need to read user data, or UserDetailsManager if you need full management capabilities.
- There are many applications like third-party applications that integrate with Google Workspace for authentication use OAuth or OpenID Connect to retrieve user details. These applications rely on Google’s identity service to authenticate users but do not manage user accounts directly.


![alt text](image-32.png)

>![NOTE]
> - You won't see directly LDAP implementation for UserDetailsManager , you need to include additional dependencies for it.

- The UserDetails interface is implemented by User along with additional methods.

![alt text](image-6.png)


- Uptil now we have seen 
	1. User Details in getting logged in the console when we added the spring security dependencies
	2. User Details in property file
	3. User Details in spring memory

- Now lets use database to store user details



























