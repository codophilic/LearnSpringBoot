package com.eazybytes.eazyschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

	/**
	 * @RequestMapping(value = "/login", method = {RequestMethod.GET})
	 *  - This annotation maps HTTP GET requests to the /login URL to this method. 
	 *  It indicates that whenever a GET request is made to /login, this method will be invoked.
	 * @RequestParam(value = "error", required = false) String error
	 *  - @RequestParam is an annotation used to extract query parameters from the URL.
	 *  - value = "error": This specifies that the method parameter error should be bound to the value of the query parameter named error.
	 *  - required = false: This indicates that the error parameter is not mandatory. If the parameter is not present in the URL, 
	 *  					the error variable will be null.
	 *  - Model model: This is an object that holds the model attributes. 
	 *  				You can add attributes to this model that will be accessible in the view (in this case, login.html).
	 */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
    		@RequestParam(value = "logout", required = false) String logout,
    		Model model) {
    	String validatonMsg=null;
    	if(null!=error) {
    		
    		validatonMsg="Incorrect Username or Password";
    	}
    	if(null!=logout) {
    		validatonMsg="You are logout successfully!!!";
    	}
		model.addAttribute("validationMessgeOnHtml",validatonMsg);
        return "login.html";
    }

}