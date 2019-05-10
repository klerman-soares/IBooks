package com.klerman.ibooks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping(value={"/", "/index"})
    public String getHomePage(Model model){

        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
    
    @GetMapping(value="/logout-success")
    public String getLogoutPage(Model model){
        return "logout";
    }
}
