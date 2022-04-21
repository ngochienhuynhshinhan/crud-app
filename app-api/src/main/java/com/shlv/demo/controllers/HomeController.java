package com.shlv.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("message", "Thank you for visiting.");
        model.addAttribute("user", true);
        return "home/index";
    }

    @GetMapping("/thymeleaf")
    public String ThymeleafController(){
        return "home/thymeleaf";
    }

    @GetMapping("/contact")
    public String ContactController(){
        return "home/contact";
    }

    @GetMapping("/about")
    public String AboutController(){
        return "home/about";
    }
}
