package com.uoc.tumesa.app.spring.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/login")
    public String loginEndpoint() {
        return "Login";
    }
}
