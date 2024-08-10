package com.spring.boot.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@RequestMapping(path="/testpage", method=RequestMethod.GET)
	@ResponseBody
	public String requestMethodName() {
		
		return "this is now it is a dynamic content";
	}
	
}
