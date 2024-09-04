package com.eazybytes.eazyschool.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserIDChecker implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		/**
		 * Converting ServletRequest To HttpServletRequest
		 */
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		/**
		 * Fetch Authorization key from Headers
		 */
		String authorizationValue=req.getHeader(HttpHeaders.AUTHORIZATION);
		
		/**
		 * "Basic based64_Encoded_Format_Username_Password
		 */
		System.out.println(authorizationValue);
		if(null != authorizationValue) {
			authorizationValue = authorizationValue.trim();
            if(StringUtils.startsWithIgnoreCase(authorizationValue, "Basic ")) {
                byte[] base64Token = authorizationValue.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, StandardCharsets.UTF_8); // un:pwd
                    int delim = token.indexOf(":");
                    if(delim== -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String userId = token.substring(0,delim);
                    System.out.println("User ID - "+userId);
                    if(userId.toLowerCase().contains("test")) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException exception) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
		
		/**
		 * Calling Next Filter chain
		 */
        chain.doFilter(request, response);
	}

}
