package com.javatechie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityDemoController {

    @GetMapping("/welcome")
    //ADMIN
    public String welcome() {
        return "Welcome to Javatechie online first batch !";
    }

    @GetMapping("/text")
    //USER
    public String greeting() {
        return "Happy to see you here !";
    }

    @GetMapping("/nonsecureapp")
    public String nonSecure() {
        return "This is non secure endpoint , you can access it no worries !";
    }
}
