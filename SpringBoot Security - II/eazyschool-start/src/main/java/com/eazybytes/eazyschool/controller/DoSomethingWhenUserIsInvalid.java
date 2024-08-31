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
