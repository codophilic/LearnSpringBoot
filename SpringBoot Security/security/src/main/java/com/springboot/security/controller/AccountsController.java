package com.springboot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

	@GetMapping("/accounts")
	public String myAccounts() {
		return "My Accounts";
	}
}
