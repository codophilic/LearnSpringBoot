package com.springboot.security.securityexceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationExceptionHandling implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		/**
		 * Headers
		 */
		response.setHeader("Custom-Header-Title", "Authentication Failed");
		response.setHeader("Custom-Header-Msg", "Invalid Email ID");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		/**
		 * Body
		 */
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message="Invalid Credentails entered like Email ID or password hence authentication failed";
        response.setContentType("application/json;charset=UTF-8");
        String jsonResponse =
                String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                        currentTimeStamp, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        message);	
        response.getWriter().write(jsonResponse);
	}

}
