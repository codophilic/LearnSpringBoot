package com.spring.boot.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Component
public class Coders {

	//Instance variable
	@Autowired
	private Bugs bugs; // field injection
	
	public Bugs getBugs() {
		return bugs;
	}

	public void setBugs(Bugs bugs) {
		this.bugs = bugs;
	}

	public void code() {
		System.out.println("Coding...");
	}
}
