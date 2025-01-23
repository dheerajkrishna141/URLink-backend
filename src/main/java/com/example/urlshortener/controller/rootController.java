package com.example.urlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Root APIs")
public class rootController {
	
	@Operation(summary = "Root path", description = "Access the root path of the application")
	@GetMapping("/")
	public ResponseEntity<String> homePage(){
		return new ResponseEntity<String>("Welcome to url_shortener backend", HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Access documentation", description="Access the redoc style documentation of the project")
	@GetMapping("/docs")
	public String getDocs() {
		return "docs";
	}
}
