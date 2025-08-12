package com.sales_control.rest.sales_api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping("/sales")
    public String welcome(Authentication authentication) {
        return "Welcome " + authentication.getName() + " from secure endpoint";
    }
}
