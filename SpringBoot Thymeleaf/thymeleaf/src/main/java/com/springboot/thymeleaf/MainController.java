package com.springboot.thymeleaf;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/welcome")
	public ModelAndView welcomePage() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("welcome");
		mav.addObject("name","Harsh");
		mav.addObject("listvalues",List.of("Item1","Item2","Item3"));
		return mav;
	}
	
	@GetMapping("/conditions")
	public ModelAndView conditionsPage() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("conditions");
		mav.addObject("userActive",false);
		mav.addObject("listsize",List.of(10,20,30));
		mav.addObject("gender", "F");
		mav.addObject("age", 100);
		return mav;
	}
	
	@GetMapping("/fragments")
	public ModelAndView fragmentsPage() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("fragments");
		return mav;
	}
	
	@GetMapping("/get-users-details")
	public ModelAndView userDetailsPage() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("HostUserDetails");
		mav.addObject("users",List.of(new User("Harsh Pandya", "Mumbai"),new User("Meet Pandya", "Mumbai")));
		return mav;
	}

	@GetMapping("/inherit")
	public ModelAndView inheritPage() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("commons/inheritwholehtml/mywebsite");
		return mav;
	}
}
