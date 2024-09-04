package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.eazybytes.eazyschool.controller.DoSomethingWhenUserIsInvalid;
import com.eazybytes.eazyschool.controller.DoSomethingWhenUserIsValid;
import com.eazybytes.eazyschool.filter.LoggingFilterAt;
import com.eazybytes.eazyschool.filter.UserIDChecker;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig {
	
	private final DoSomethingWhenUserIsInvalid doSomethingWhenUserIsInvalid;
	
	private final DoSomethingWhenUserIsValid doSomethingWhenUserIsValid;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
//        .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**","/login/**").permitAll())
                .formLogin(i->i.loginPage("/login").usernameParameter("customerId").passwordParameter("custPass")
                		.failureUrl("/login?error").successHandler(doSomethingWhenUserIsValid)
                		.failureHandler(doSomethingWhenUserIsInvalid)
                		)
                .httpBasic(Customizer.withDefaults()); 
        
        http.logout(i->i.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
        		.clearAuthentication(true).deleteCookies("JSESSIONID"));
        
        /**
         * Check if user id contains 'test'
         */
        http.addFilterAfter(new UserIDChecker(), BasicAuthenticationFilter.class);
        
        /**
         * Logging Filter At
         */
        http.addFilterAt(new LoggingFilterAt(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("defaultUserName")
                .password("{noop}codophillic@123").authorities("read").build();
        UserDetails admin = User.withUsername("defaultAdminUser")
                .password("{bcrypt}$2a$12$KU4D4fSOMRYtbUJuFxiI4.jHHEesJ9SJaQj2npyh.6WKIRdoyVA8i") // codophillic@123
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Security 6.3 version
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }


}
