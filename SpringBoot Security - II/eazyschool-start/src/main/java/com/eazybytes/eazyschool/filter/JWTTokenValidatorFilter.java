package com.eazybytes.eazyschool.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eazybytes.eazyschool.constant.ApplicationConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Here will be logic of validating the token
		
		/**
		 * The request will be sending JWT Token inside its header, so from the header
		 * we need to fetch the token
		 */
	       String jwt = request.getHeader("EazyBankJWTAuthorization");
	       if(null != jwt) {
	           try {
	        	   
	        	   /**
	        	    * From Environment fetch the secret key
	        	    */
	               Environment env = getEnvironment();
	               if (null != env) {
	                   String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
	                           ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
	                   
	                   /**
	                    * Generating the secret key
	                    */
	                   SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	                   if(null !=secretKey) {
	                	   
	                	   /**
	                	    * `Jwts.parser().verifyWith(secretKey).build()` verifies the token
	                	    * using secret key if any exception caught which means token is being tampered then
	                	    * the catch block will be executed.
	                	    * 
	                	    * If token is valid then using `parseSignedClaims` we will fetch
	                	    * username and authorities which were set during jwt token generation
	                	    */
	                	   
	                       Claims claims = Jwts.parser().verifyWith(secretKey)
	                                .build().parseSignedClaims(jwt).getPayload();
	                       String username = String.valueOf(claims.get("username"));
	                       String authorities = String.valueOf(claims.get("authorities"));
	                       
	                       /**
	                        * Creating authentication object and setting that 
	                        * into securityContextHolder, here password is kept null since authentication
	                        * is already done using jwt token.
	                        */
	                       Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
	                               AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
	                       SecurityContextHolder.getContext().setAuthentication(authentication);
	                   }
	               }

	           } catch (Exception exception) {
	               throw new BadCredentialsException("Invalid Token received!");
	           }
	       }
	        filterChain.doFilter(request,response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		/**
		 * When we write request.getServletPath().equals("/login") and if the user enters /dashboard path, 
		 * which requires authentication, here we need to invoke the validator filter
		 * so request.getServletPath().equals("/login") will return false, which indicates that 
		 * filer should be executed.
		 * 
		 */
		
		return request.getServletPath().equals("/login");
	}

}
