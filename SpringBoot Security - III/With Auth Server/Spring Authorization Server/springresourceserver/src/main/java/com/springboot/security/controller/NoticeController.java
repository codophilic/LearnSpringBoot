package com.springboot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {

	@GetMapping("/notices")
	public String getNotice() {
		return "Bank Notice";
	}
}
