package com.springboot.security.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeniedController {

	@GetMapping("/denied")
	public String deniedHandler() {
		return "You are authenticated but not authorized to access this page";
	}
}
