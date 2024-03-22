package com.aquent.crudapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Redirect to the landing page 
 */
@Controller
public class HomeController {
    /**
     * Redirect to the home page to view a list of options.
     *
     * @return view with navigation options to people or clients page.
     */
	@GetMapping("/")
    public ModelAndView index() {
		 ModelAndView mav = new ModelAndView("home");
        return mav;
    }
	 
}
