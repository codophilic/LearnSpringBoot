package com.springboot.security.authenticationprovider;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.security.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Profile("!security_production")
public class CustomAuthenticationProviderNonProd implements AuthenticationProvider{

	private final CustomerService customerService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String inputMailId=authentication.getName();

		UserDetails uc=customerService.loadUserByUsername(inputMailId);

		return new UsernamePasswordAuthenticationToken(uc.getUsername(),uc.getPassword(),uc.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
