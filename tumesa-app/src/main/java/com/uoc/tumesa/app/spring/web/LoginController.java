package com.uoc.tumesa.app.spring.web;

import com.uoc.tumesa.app.spring.sec.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public String login() {
        return jwtService.generateToken("4444");
    }
}
