package com.aquent.crudapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Simple controller to redirect to the person listing.  In the future, we could
 * add other landing page behavior here if we were writing a real application.
 */
@Controller
public class HomeController {
    /**
     * Redirect to the home page to view  a list of options.
     *
     * @return view with navigation options to people or clients page.
     */
	@GetMapping("/")
    public ModelAndView index() {
		 ModelAndView mav = new ModelAndView("home");
        return mav;
    }
	 
}
