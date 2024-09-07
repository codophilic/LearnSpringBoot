package com.eazybytes.eazyschool.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.eazybytes.eazyschool.controller.DoSomethingWhenUserIsInvalid;
import com.eazybytes.eazyschool.controller.DoSomethingWhenUserIsValid;
import com.eazybytes.eazyschool.filter.JWTTokenGeneratorFilter;
import com.eazybytes.eazyschool.filter.JWTTokenValidatorFilter;
import com.eazybytes.eazyschool.filter.LoggingFilterAt;
import com.eazybytes.eazyschool.filter.UserIDChecker;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class ProjectSecurityConfig{
	
	private final DoSomethingWhenUserIsInvalid doSomethingWhenUserIsInvalid;
	
	private final DoSomethingWhenUserIsValid doSomethingWhenUserIsValid;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("EazyBankJWTAuthorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers( "/login","/contact") // Allowing List of Path which does not requires CSRF validation
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard","/notallowed","/allidname").authenticated()
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
        
        /**
         * Adding JWTTokenGeneratorFilter after BasicAuthenticationFilter
         * Since we're looking for an option to generate the Jot token once the authentication is successful,
         */
        http.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class);
        
        /**
         * Adding JWTTokenValidatorFilter before BasicAuthenticationFilter
         * Inside the JWTTokenValidatorFilter, we're going to validate if the JWT token is valid.
         * If yes, we're going to let the Spring Security framework that the authentication is already 
         * validated and successful, don't try to perform the authentication one more time.
         * So since we are looking for an option to execute our filter
         * before the authentication
         */
        http.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);

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
