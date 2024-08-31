package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Customize Spring MVC configuration.
	 * This method is used to register simple automated controllers pre-configured with a response status 
	 * code or a view to render. By implementing WebMvcConfigurer and overriding this method, 
	 * you can map URLs directly to views without the need for a controller class.
	 * 
	 * Under Controller package we won't have any controller for Courses and About
	 * When a user navigates to the URL /courses, Spring MVC will render the view associated with
	 * the view name "courses". The view name corresponds to a file (e.g., courses.html in the 
	 * src/main/resources/templates directory using Thymeleaf.
	 * 
	 * When a user navigates to /about, Spring MVC will render the view associated with the view name "about".
	 * 
	 * This is useful when your html contents are static.
	 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/courses").setViewName("courses");
        registry.addViewController("/about").setViewName("about");
    }

}
