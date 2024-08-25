package com.springboot.security.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.springboot.security.securityexceptionhandling.CustomAuthenticationExceptionHandling;
import com.springboot.security.securityexceptionhandling.CustomAuthorizationExceptionHandling;

@Configuration(enforceUniqueMethods = true)
@Profile("security_production")
public class ProjectSecurityConfiguration {

	/**
	 * Customize Spring Securities
	 * - permitAll() -> Allows end user to access all the pages without asking any logging credentials
	 * - denyAll() -> Allows end user to perfom login but denies end user to access the page even though the user is authorized to access it.
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.requiresChannel(rcc->rcc.anyRequest().requiresSecure()) // ONLY HTTPS ALLOWED
		.csrf(i->i.disable())
		.authorizeHttpRequests((requests) -> requests.
				requestMatchers("/accounts","/balance","/cards","/loans").authenticated().
				requestMatchers("/contact","/notice","/customer-registration","/fetch-customer/{emailId}").permitAll()
				);
		http.formLogin(withDefaults());
		//http.formLogin(i->i.disable());
		http.httpBasic(i->i.authenticationEntryPoint(new CustomAuthenticationExceptionHandling()));
//		http.httpBasic(withDefaults());
		//http.httpBasic(i->i.disable());
		return http.build();
	}
	
	/**
	 * - To use InMemoryUserDetailsManager , comment out JdbcUserDetailsManager implementation
	 */
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails u1=User.withUsername("user1").password("{noop}thisCouldBe@1234").authorities("read").build();
//		UserDetails u2=User.withUsername("user2").password("{bcrypt}$2a$12$ThHcCo7ZvUJ4QjCsaoy87e74mwzP5fhzWA4MxDwaNOU0bxqoOv.Aa").authorities("admin").build();
//		return new InMemoryUserDetailsManager(u1,u2);
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		//return new BCryptPasswordEncoder();
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}
}
