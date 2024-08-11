package com.springboot.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class ApiController {

	/*
	 * @RequestMapping(path="/sample",method = RequestMethod.GET)
	 * 
	 * @ResponseBody public String sampleMethod() { return
	 * "This is a sample response"; }
	 */
	
	@GetMapping("/sample")
	public String sampleMethod() {
		return "This is a sample response";
	}
}
