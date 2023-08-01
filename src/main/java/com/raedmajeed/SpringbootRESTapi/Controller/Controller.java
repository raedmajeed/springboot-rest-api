package com.raedmajeed.SpringbootRESTapi.Controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class Controller {
    @GetMapping("/")
    public String initialRun() {
        return "hello world";
    }
 }
