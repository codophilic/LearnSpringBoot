package com.springboot.securityWithoutAuth.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	
	@GetMapping("/sample")
	public String SamplePage(Authentication auth) {
        if(auth instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
            System.out.println(usernamePasswordAuthenticationToken);
        } else if (auth instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            System.out.println(oAuth2AuthenticationToken);
        }
		return "This is a sample page";
	}
}
