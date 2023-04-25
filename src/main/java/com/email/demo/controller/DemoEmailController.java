package com.email.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.email.demo.service.EmailService;

@RestController
public class DemoEmailController {
	
	@Autowired
	EmailService emailService;
	
	
	@GetMapping("/hello")
	public String getHelloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/email/{name}")
	public String getHappyBirthdayEmail(@PathVariable("name") String name) {
		emailService.emailHappyBirthDay(name);
		return "Hello World";
	}

}
