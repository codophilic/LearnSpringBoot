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
