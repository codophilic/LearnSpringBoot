package com.spring.boot.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Component
public class Coders {

	//Instance variable
	@Autowired
	private Bugs bugs; // field injection
	
	private Error error;
	
	private Lines lines;
	
	public Bugs getBugs() {
		return bugs;
	}

	public Error getError() {
		return error;
	}

	/**
	 * Setters Injection
	 */
	@Autowired
	public void setError(Error error) {
		this.error = error;
	}

	public void setBugs(Bugs bugs) {
		this.bugs = bugs;
	}

	public void code() {
		System.out.println("Coding...");
		lines.linesOfCodes(); // Utilizing the method via constructor injection
	}
	
	/**
	 * Constructor Injection
	 */
	@Autowired
	public Coders(Lines lines) {
		this.lines=lines;
	}
	
}
