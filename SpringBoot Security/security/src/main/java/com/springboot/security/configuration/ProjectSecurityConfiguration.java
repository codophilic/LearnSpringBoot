package com.springboot.security.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfiguration {

	/**
	 * Customize Spring Securities
	 * - permitAll() -> Allows end user to access all the pages without asking any logging credentials
	 * - denyAll() -> Allows end user to perfom login but denies end user to access the page even though the user is authorized to access it.
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.
				requestMatchers("/accounts","/balance","/cards","/loans").authenticated().
				requestMatchers("/contact","/notice").permitAll()
				);
		http.formLogin(withDefaults());
		//http.formLogin(i->i.disable());
		http.httpBasic(withDefaults());
		//http.httpBasic(i->i.disable();
		return http.build();
	}
}
