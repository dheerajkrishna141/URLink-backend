package com.example.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.payload.userDTO;
import com.example.urlshortener.service.userService;
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class UserController {
	@Autowired
	private userService userService; 
	
	@PostMapping("/register")
	public ResponseEntity<userDTO> createUser(@RequestBody userDTO userdto){
		return new ResponseEntity<>(userService.createUser(userdto), HttpStatus.CREATED);
		
	}
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody userDTO userdto){
		return new ResponseEntity<>(userService.loginUser(userdto), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name="id") long id){
		userService.deleteUser(id);
		return new ResponseEntity<String>("User with ID: "+id+" has been deleted succesfully.", HttpStatus.OK);
	}

}
