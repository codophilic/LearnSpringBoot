package com.springboot.security.authenticationprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.springboot.security.service.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	private final CustomerService customerService;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/**
		 * Input from page or API
		 */
		String inputMailId=authentication.getName();
		String inputPwd=authentication.getCredentials().toString();

		/**
		 * Custom logic like age > 10 here we are just specifying user must be abc@gmail.com only
		 * PasswordEncoder is require to perform encoded match between the input entered password and the password 
		 * in database
		 */
		if(!("abc@gmail.com").equals(inputMailId)) {
			System.out.println("UnAuthorized Email ID, message from Custom Authentication");
			throw new BadCredentialsException("UnAuthorized Email ID, message from Custom Authentication");
		}
		
		
		/**
		 * Fetch Details from database
		 */
		UserDetails uc=customerService.loadUserByUsername(inputMailId);
		
		if(passwordEncoder.matches(inputPwd, uc.getPassword())) {
			return new UsernamePasswordAuthenticationToken(uc.getUsername(),uc.getPassword(),uc.getAuthorities());
		}
		
		/**
		 * AuthenticationException is an abstract class which is implemented by multiple exceptions like
		 * - BadCredentialsException
		 * - UsernameNotFoundException ( this exception is thrown by loadByUserName of UserDetailsService )
		 * .. and  many more
		 */
		throw new BadCredentialsException("Invalid Credentials");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
