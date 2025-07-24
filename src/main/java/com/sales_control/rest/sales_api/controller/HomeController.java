package com.sales_control.rest.sales_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @PostMapping(value = "sales")
    public String welcome(){
        return "Welcome form secure endpoint";
    }
}
