package com.springboot.security.securityexceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthorizationExceptionHandling implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		/**
		 * Headers
		 */
		response.setHeader("Custom-Header-Title", "Authorization Failed");
		response.setHeader("Custom-Header-Msg", "Required privilege not found");
        response.setStatus(HttpStatus.FORBIDDEN.value());
		
		/**
		 * Body
		 */
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message="Required privilege not found hence user not authorized";
        response.setContentType("application/json;charset=UTF-8");
        String jsonResponse =
                String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                        currentTimeStamp, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        message);	
        response.getWriter().write(jsonResponse);
	}

}
