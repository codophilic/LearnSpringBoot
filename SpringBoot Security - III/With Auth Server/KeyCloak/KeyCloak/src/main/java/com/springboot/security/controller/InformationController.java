package com.springboot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationController {

	@GetMapping("/info")
	public String shareInfo() {
		return "This is just a service which only shares info without any end user dependency";
	}
}
