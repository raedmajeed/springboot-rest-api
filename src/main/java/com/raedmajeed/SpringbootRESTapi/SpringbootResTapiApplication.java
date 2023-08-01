package com.raedmajeed.SpringbootRESTapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class SpringbootResTapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootResTapiApplication.class, args);
	}

	@GetMapping("/")
	public String ru() {
		return "hie";
	}

}
