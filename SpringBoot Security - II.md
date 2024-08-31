# Spring Boot Security - II

- So here we have a spring boot MVC application which has frontend based on HTML & Thymeleaf. 

<video controls src="20240831-1207-25.6853539.mp4" title="Title"></video>

- Here we have images, js, css and webfonts file . These are static files. So it is specified under static folder.

![alt text](image.png)

- All the HTML files are present under folder template

![alt text](image-1.png)

- We have different controller

1. Home Page Controller (default page)

```
package com.eazybytes.eazyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	/**
	 * Specifying "" or "/" makes home.html default page
	 */
    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage() {
        return "home.html";
    }

}
```

2. Login Controller

```
package com.eazybytes.eazyschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String displayLoginPage() {
        return "login.html";
    }

}
```

3. Dashboard Controller

```
package com.eazybytes.eazyschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model,Authentication authentication) {
        if(null != authentication) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities().toString());
        }
        return "dashboard.html";
    }

}
```

4. Holiday Controller

```
package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Holiday;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class HolidaysController {

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display,Model model) {
        if(null != display && display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("federal")){
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("festival")){
            model.addAttribute("festival",true);
        }
        List<Holiday> holidays = Arrays.asList(
                new Holiday(" Jan 1 ","New Year's Day", Holiday.Type.FESTIVAL),
                new Holiday(" Oct 31 ","Halloween", Holiday.Type.FESTIVAL),
                new Holiday(" Nov 24 ","Thanksgiving Day", Holiday.Type.FESTIVAL),
                new Holiday(" Dec 25 ","Christmas", Holiday.Type.FESTIVAL),
                new Holiday(" Jan 17 ","Martin Luther King Jr. Day", Holiday.Type.FEDERAL),
                new Holiday(" July 4 ","Independence Day", Holiday.Type.FEDERAL),
                new Holiday(" Sep 5 ","Labor Day", Holiday.Type.FEDERAL),
                new Holiday(" Nov 11 ","Veterans Day", Holiday.Type.FEDERAL)
        );
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }

}
```

![alt text](image-2.png)


5. Contact Controller


```
package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }


    @RequestMapping(value = "/saveMsg",method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : " + errors.toString());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        contactService.setCounter(contactService.getCounter()+1);
        log.info("Number of times the Contact form is submitted : "+contactService.getCounter());
        return "redirect:/contact";
    }



}
```


![alt text](image-3.png)


- Now to store contact details we would require contact DTO and a service layer.

```
Contact Entity

package com.eazybytes.eazyschool.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.validation.constraints.*;

/*
@Data annotation is provided by Lombok library which generates getter, setter,
equals(), hashCode(), toString() methods & Constructor at compile time.
This makes our code short and clean.
* */
@Data
public class Contact {

    /*
    * @NotNull: Checks if a given field is not null but allows empty values & zero elements inside collections.
      @NotEmpty: Checks if a given field is not null and its size/length is greater than zero.
      @NotBlank: Checks if a given field is not null and trimmed length is greater than zero.
    * */
    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNum;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Subject must not be blank")
    @Size(min=5, message="Subject must be at least 5 characters long")
    private String subject;

    @NotBlank(message="Message must not be blank")
    @Size(min=10, message="Message must be at least 10 characters long")
    private String message;
}



Service Layer

package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Slf4j
@Service
// @RequestScope
// @SessionScope
@ApplicationScope
public class ContactService {

    private int counter = 0;

    public ContactService(){
        System.out.println("Contact Service Bean initialized");
    }

    /**
     * Save Contact Details into DB
     * @param contact
     * @return boolean
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        //TODO - Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
```

- Currently behind there are no databases involved, its just simple **InMemoryUserDetailsManager**. Below is the project security configuration.

```
package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").permitAll()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("defaultUserName")
                .password("{noop}codophillic@123").authorities("read").build();
        UserDetails admin = User.withUsername("defaultAdminUser")
                .password("{bcrypt}$2a$12$KU4D4fSOMRYtbUJuFxiI4.jHHEesJ9SJaQj2npyh.6WKIRdoyVA8i") // codophillic@123
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Security 6.3 version
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }


}
```

- Here since we have assets which consist of static files , so all the urls generated using asset needs to have `permitAll` (`/assets/**`). Similarly for `/holidays/**`.
- So whatever present inside the asset folder we want to be accessible without any security. That's why we have mentioned assets, asterisk, asterisk. That means anything that is coming after the assets path I want to `permitAll`. The same applies for holidays, as well. After the holidays path, if there is any subsequent path, I also want to `permitAll()`.
- Example `/holidays/all` or any sort of url which gets appended as suffix must have `permitAll` .

![alt text](image-4.png)

- Below is the Web MVC Configuration.

```
package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Customize Spring MVC configuration.
	 * This method is used to register simple automated controllers pre-configured with a response status 
	 * code or a view to render. By implementing WebMvcConfigurer and overriding this method, 
	 * you can map URLs directly to views without the need for a controller class.
	 * 
	 * Under Controller package we won't have any controller for Courses and About
	 * When a user navigates to the URL /courses, Spring MVC will render the view associated with
	 * the view name "courses". The view name corresponds to a file e.g., courses.html in the 
	 * src/main/resources/templates directory using Thymeleaf.
	 * 
	 * When a user navigates to /about, Spring MVC will render the view associated with the view name "about".
     * This is useful when your html contents are static.
	 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/courses").setViewName("courses");
        registry.addViewController("/about").setViewName("about");
    }

}
```

- Currently there are no profiles created for this application. We are using **default** profile.
- This was about the required overview of the project. Currently we have been using springboot defaults login page. What if we wanted to customize and add our own?
- We need to customize `formLogin`.

```
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").permitAll()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**").permitAll())
                .formLogin(i->i.loginPage("/login"))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
```

- Lets run the application and go to the login page

![alt text](image-5.png)

- It says too many re-directs because the problem right now we have is, framework is trying to redirect it to the login page for authentication, but we have not specified that to access login page no authentication is required. By default spring security perform authentication for any page and login itself is the UI for authentication that's why it is again trying to redirect it.
- Provide the url under `permitAll`.

![alt text](image-6.png)

- Lets make `/dashboard` as secured, because user must perform authentication then only dashboard must be accessible.

```
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**","/login").permitAll())
                .formLogin(i->i.loginPage("/login"))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
```

<video controls src="20240831-1640-03.4579858.mp4" title="Title"></video>

- So if user tries to access dashboard , he or she will be redirected to login page. Lets try to login on dashboard.

![alt text](image-7.png)

- The user name **defaultAdminUser** is populated using **Authentication** Object inside the dashboard controller

```
public class DashboardController {

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model,Authentication authentication) {
        if(null != authentication) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities().toString());
        }
        return "dashboard.html";
    }

}
```

- The name and authorized are set using model and those data are fetched using thymeleaf.

```
<!-- login block -->
<section class="w3l-contact py-5" id="contact">
    <div class="container py-md-5 py-4">
        <div class="title-main text-center mx-auto mb-md-5 mb-4" style="max-width:500px;">
            <h3 class="title-style" th:text="${'Welcome - ' + username}"></h3>
            <p class="" th:text="${'You logged in as - ' + roles}"></p>
        </div>

    </div>
</section>
```

- Lets look the **login.html** which accepts user name and password.

```
                <form action="/login" method="post" class="signin-form">
                    <div class="col-md-8 login-center input-grids">
                        <li class="alert alert-danger" role="alert" th:if="${!#strings.isEmpty(validationMessgeOnHtml)}"
                            th:text="${validationMessgeOnHtml}" />
                        <input type="text" name="username" id="username" placeholder="Username"
                               class="login-input" />
                        <input type="password" name="password" id="password" placeholder="Password"
                               class="login-input" />
                    </div>
                    <div class="col-md-8 login-center text-start">
                        <button class="btn btn-style btn-style-3 text-left">Log In</button>
                        <a class="new-user text-right" href="">New User ?</a>
                    </div>
                </form>
```

- If you see , to accept user name and password we have two fields `name="username"` and `name="password"`. Now during authentication where the values of these parameters get stored? how the authentication happens using html attributes?
- Here we are using default authentication, so **UsernamePasswordAuthenticationFilter** is invoked. This filter consist of two final variables

![alt text](image-8.png)

- The variable values are same as html parameter name values. If we run the application in debug mode we can see the user name , fetch from the request.

![alt text](image-9.png)

- Lets say if we want to customize the name field in html for user name and password.

```
                        <input type="text" name="customerId" id="username" placeholder="Username"
                               class="login-input" />
                        <input type="password" name="custPass" id="password" placeholder="Password"
                               class="login-input" />
```

- In that case, we need to specify that our request attributes will be `customerId` and `custPass` to spring security in project security configuration.

```
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**","/login").permitAll())
                .formLogin(i->i.loginPage("/login").usernameParameter("customerId").passwordParameter("custPass"))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
```

- What if the user enters wrong credentials?

<video controls src="20240831-1717-17.1080149.mp4" title="Title"></video>

- If you see the url gets changed to `http://localhost:8080/login?error` but there is nothing notified about that if the user credentails are wrong. How to handle this in a customize form page?
- So we need to configure a default **failureUrl**. To allow everyone (including unauthenticated users) to access the login page, you need to permit access to /login and any related URLs in your Spring Security configuration (`**/login/**`).

```
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**","/login/**").permitAll())
                .formLogin(i->i.loginPage("/login").usernameParameter("customerId").passwordParameter("custPass")
                		.failureUrl("/login?error"))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
```

- Now the `error=true` variable needs to be bind with a string variable using `@RequestParam`

```
	/**
	 * @RequestMapping(value = "/login", method = {RequestMethod.GET})
	 *  - This annotation maps HTTP GET requests to the /login URL to this method. 
	 *  It indicates that whenever a GET request is made to /login, this method will be invoked.
	 * @RequestParam(value = "error", required = false) String error
	 *  - @RequestParam is an annotation used to extract query parameters from the URL.
	 *  - value = "error": This specifies that the method parameter error should be bound to the value of the query parameter named error.
	 *  - required = false: This indicates that the error parameter is not mandatory. If the parameter is not present in the URL, 
	 *  					the error variable will be null.
	 *  - Model model: This is an object that holds the model attributes. 
	 *  				You can add attributes to this model that will be accessible in the view (in this case, login.html).
	 */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,Model model) {
    	String validatonMsg=null;
    	if(null!=error) {
    		
    		validatonMsg="Incorrect Username or Password";
    	}
		model.addAttribute("validationMessgeOnHtml",validatonMsg);
        return "login.html";
    }
```

- We have set a model attribute with name `validationMessgeOnHtml` , now this needs to be fetch on frontend by thymeleaf.

```
                    <div class="col-md-8 login-center input-grids">
                        <li class="alert alert-danger" role="alert" th:if="${!#strings.isEmpty(validationMessgeOnHtml)}"
                            th:text="${validationMessgeOnHtml}" />
                        <input type="text" name="customerId" id="username" placeholder="Username"
                               class="login-input" />
                        <input type="password" name="custPass" id="password" placeholder="Password"
                               class="login-input" />
                    </div>
```

![alt text](image-10.png)


- Lets say you wanna log something incase of a user credentials are valid or invalid, when the click on **LogIn** button, this can be achieved by using **AuthenticationSuccessHandler** and **AuthenticationFailureHandler**.

```
package com.eazybytes.eazyschool.controller;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DoSomethingWhenUserIsValid implements AuthenticationSuccessHandler{

	/**
	 * This interface is used to define what happens after a user successfully logs in. 
	 * You can customize actions like redirecting the user to a specific page or displaying a success message.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("User credentials are valid - "+authentication.getName());
		
		/**
		 * The default behavior of Spring Security is to automatically handle redirections or 
		 * forward requests based on predefined settings like loginPage(), failureUrl(), or using default success URLs
		 * in project security configurations.
		 * However, when you provide custom handlers (successHandler and failureHandler), you need to 
		 * explicitly define the actions to take in those handlers because Spring Security expects that you, 
		 * as the developer, want to customize every aspect of the response.
		 * 
		 * By using custom handlers, you override the default handling mechanism of Spring Security. 
		 * Therefore, it's up to you to specify what happens next, including sending a redirect or 
		 * returning a specific response
		 */
		response.sendRedirect("/dashboard");  // Explicitly redirecting to the dashboard page
        // If not specified sendRedirect you will get a blank page
	}

}


package com.eazybytes.eazyschool.controller;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DoSomethingWhenUserIsInvalid implements AuthenticationFailureHandler{
	
	public static int attempts=0;
	
	/**
	 * This interface handles login failures. It allows you to define actions when authentication fails, 
	 * such as redirecting to a login page with an error message or logging the failure event.
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.warn("User credentials are invalid - "+exception.getMessage()+" Number of attempts -"+attempts);
		attempts+=1;
		response.sendRedirect("/login?error=true");
	}



}
```

- Here for invalid we have initialized a attempt counts, so whenever a user tries to attempts login , if the credentials are invalid we can see the numbers of attempts perform for it.
- Injecting these handlers beans via constructor injection for it in project security configuration.

```
package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.eazybytes.eazyschool.controller.DoSomethingWhenUserIsInvalid;
import com.eazybytes.eazyschool.controller.DoSomethingWhenUserIsValid;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProjectSecurityConfig {
	
	private final DoSomethingWhenUserIsInvalid doSomethingWhenUserIsInvalid;
	
	private final DoSomethingWhenUserIsValid doSomethingWhenUserIsValid;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**","/login/**").permitAll())
                .formLogin(i->i.loginPage("/login").usernameParameter("customerId").passwordParameter("custPass")
                		.failureUrl("/login?error").successHandler(doSomethingWhenUserIsValid)
                		.failureHandler(doSomethingWhenUserIsInvalid)
                		)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("defaultUserName")
                .password("{noop}codophillic@123").authorities("read").build();
        UserDetails admin = User.withUsername("defaultAdminUser")
                .password("{bcrypt}$2a$12$KU4D4fSOMRYtbUJuFxiI4.jHHEesJ9SJaQj2npyh.6WKIRdoyVA8i") // codophillic@123
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Security 6.3 version
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }


}
```

<video controls src="20240831-1826-06.0477170.mp4" title="Title"></video>

- We can see the log entries on the console.
- Can we have our own logout page? - yes, just like the steps we performed for login error process, same steps can be applied for logout.
- In the project security we need to use **logoutSuccessUrl** method.

```
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**","/login/**").permitAll())
                .formLogin(i->i.loginPage("/login").usernameParameter("customerId").passwordParameter("custPass")
                		.failureUrl("/login?error").successHandler(doSomethingWhenUserIsValid)
                		.failureHandler(doSomethingWhenUserIsInvalid)
                		)
                .httpBasic(Customizer.withDefaults());
        
        http.logout(i->i.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
        		.clearAuthentication(true).deleteCookies("JSESSIONID"));

        return http.build();
    }
```

- Login controller

```
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
    		@RequestParam(value = "logout", required = false) String logout,
    		Model model) {
    	String validatonMsg=null;
    	if(null!=error) {
    		
    		validatonMsg="Incorrect Username or Password";
    	}
    	if(null!=logout) {
    		validatonMsg="You are logout successfully!!!";
    	}
		model.addAttribute("validationMessgeOnHtml",validatonMsg);
        return "login.html";
    }
```

![alt text](image-11.png)

- In Spring Security, when you configure logout functionality using `.logout()` in your security configuration, you are defining how the application should handle user logout operations. While Spring Security provides a default behavior for logout, such as invalidating the session and clearing authentication, it does not automatically perform all tasks unless explicitly configured.
- `.invalidateHttpSession(true)` : Ensures that the current HTTP session is invalidated when the user logs out. This is crucial for ensuring that any data associated with the session (like user-specific data or session-scoped beans) is removed, preventing any unauthorized access.

- `.clearAuthentication(true)` : Clears the authentication object stored in the `SecurityContext` to ensure no user data is retained after logout. This is important for security to make sure there’s no residual authentication information left after the user logs out. 

- `.deleteCookies("JSESSIONID")` : Deletes the specified cookies from the browser. Removing the `JSESSIONID` cookie helps ensure that the session is fully terminated on the client side as well, enhancing security by preventing the reuse of an old session ID.


![alt text](image-12.png)

- Lets say your `/dashboard` url must only be visible when the user with valid credentials login, other user who are anonymous must not able to see the `/dashboard` page. We can achieve this using built-in method provided by thymeleaf for spring security.
- In your **headers.html** add the below namespace

```
xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity6"
```

- For all your re-directing url specify if authentication is required or not 

```
<div sec:authorize="isAnonymous()">
    <!-- Content visible only to anonymous (not logged in) users -->
</div>

<div sec:authorize="isAuthenticated()">
    <!-- Content visible only to authenticated (logged in) users -->
</div>

```

- Thymeleaf with Spring Security provides you to control the visibility of HTML elements based on the authentication status or roles of the user.
- The `sec:authorize` attribute is used to conditionally display or hide parts of the HTML content based on the user's authentication status or roles. `isAnonymous()` this expression returns true if the user is not authenticated (i.e., the user is browsing anonymously or not logged in).
- Thymeleaf with spring security allows you to dynamically display or hide content based on whether the user is logged in or not, and also based on the user's roles and permissions. This is particularly useful for personalizing the user experience and enhancing security by hiding sensitive elements from unauthorized users.

<video controls src="20240831-1915-07.3404230.mp4" title="Title"></video>

**How It Works?**
    - Integration with Spring Security: Thymeleaf, with the help of thymeleaf-extras-springsecurity6, integrates with Spring Security to evaluate expressions like isAnonymous() and isAuthenticated(). It checks the user's current security context to determine their authentication status or roles.
    - Rendering Logic: When Thymeleaf processes the HTML template, it evaluates each sec:authorize expression. If the expression evaluates to true, the element and its content are rendered in the final HTML output. If the expression evaluates to false, the element is not included in the output.
















































