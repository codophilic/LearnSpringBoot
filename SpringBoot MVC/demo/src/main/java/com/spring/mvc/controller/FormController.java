package com.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.entities.UserForm;

import jakarta.validation.Valid;

@Controller
public class FormController {
	

    @GetMapping("/form")
    public ModelAndView showForm() {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("storeUserFormDetails", new UserForm());
    	mav.setViewName("forms/form");
        return mav;
    }

    @PostMapping("/submitForm")
    public ModelAndView submitForm(@Valid @ModelAttribute("storeUserFormDetails") UserForm userForm, BindingResult formsDetailsResult) {
    	ModelAndView mav = new ModelAndView();
    	
    	if(formsDetailsResult.hasErrors()) {
    		System.out.println("Issue in details"+formsDetailsResult);
    		mav.setViewName("forms/form");
    		return mav;
    	}
    	
    	mav.addObject("user", userForm);
    	mav.setViewName("forms/result");
        return mav;
    }
	
}
