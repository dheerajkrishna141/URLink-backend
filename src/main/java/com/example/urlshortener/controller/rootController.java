package com.example.urlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class rootController {
	
	@GetMapping("/")
	public ResponseEntity<String> homePage(){
		return new ResponseEntity<String>("Welcome to url_shortener backend", HttpStatus.ACCEPTED);
	}

}
