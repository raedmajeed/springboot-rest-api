package com.raedmajeed.SpringbootRESTapi.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayrollController {
    @GetMapping("/hello")
    public String insert() {
        System.out.println("Raed");
        return "raed";
    }
}
