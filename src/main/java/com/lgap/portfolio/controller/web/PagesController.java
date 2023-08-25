package com.lgap.portfolio.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagesController {
    @GetMapping("/")
    public String showIndex(Model theModel){
        return "index";
    }

    @GetMapping("/contact")
    public String showContact(Model theModel){
        return "index";
    }

    @GetMapping("/getFragment")
    public String getFragment(@RequestParam("name") String fragmentName) {
        return "fragments/" + fragmentName;
    }

}
