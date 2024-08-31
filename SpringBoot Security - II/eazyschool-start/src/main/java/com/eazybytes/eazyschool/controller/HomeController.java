package com.eazybytes.eazyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	/**
	 * Specifying "" or "/" makes home.html default page
	 */
    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage() {
        return "home.html";
    }

}
