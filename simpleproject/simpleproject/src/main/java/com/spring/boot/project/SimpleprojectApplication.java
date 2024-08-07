package com.spring.boot.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SimpleprojectApplication {

	public static void main(String[] args) {
		
		//IOC Container
		ApplicationContext context=SpringApplication.run(SimpleprojectApplication.class, args);
		//Get bean
		Coders coders=context.getBean(Coders.class);
		coders.code();
		coders.getBugs().fixBugs();
		coders.getError().showError();
	}

}
