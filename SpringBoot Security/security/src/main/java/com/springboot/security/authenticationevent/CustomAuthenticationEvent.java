package com.springboot.security.authenticationevent;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j // Used for logging
public class CustomAuthenticationEvent {
	
	@EventListener
	public void onUserSuccess(AuthenticationSuccessEvent successEvent) {
		log.info("Login successful for the user : {}", successEvent.getAuthentication().getName());
	}
	
	@EventListener
	public void onUserFailure(AbstractAuthenticationFailureEvent failureEvent) {
		log.error("Login failed for the user : {} due to : {}", failureEvent.getAuthentication().getName(),
				failureEvent.getException().getMessage());
	}
}
