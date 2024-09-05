package com.eazybytes.eazyschool.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eazybytes.eazyschool.constant.ApplicationConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Here will be logic of generating the token
		
		/**
		 * Post Basic Authentication is done, the authentication details will be stored inside the 
		 * Security Context, so from there we need to fetch the authentication details
		 */
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(null!=authentication) {
			
			/**
			 * To generate JWT Token , we require a secret key, now this secret key must be present
			 * in environment variables, here we have create a constant class to define such things
			 * and using environment method provided by spring we will fetch it
			 */
            Environment env = getEnvironment();
			if (null != env) {
				
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                
                System.out.println("Secret - "+secret);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String jwt = Jwts.builder().issuer("Eazy Bank").subject("Eazy Bank JWT Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 30000000))
                        .signWith(secretKey).compact();
                response.setHeader("JWT Authorization", jwt);
            }
        }
        filterChain.doFilter(request, response);			
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		/**
		 * When we write request.getServletPath().equals("/login") and if the user enters /login path, 
		 * the condition will be true and shouldNotFilter() will return true, which indicates that
		 * the doFilterInternal will not be invoked since return boolean type is true.
		 * 
		 * So using !request.getServletPath().equals("/login"), it will return false, which 
		 * states that the doFilterInternal should be executed.
		 * 
		 */
		
		return !request.getServletPath().equals("/login");
	}

}
