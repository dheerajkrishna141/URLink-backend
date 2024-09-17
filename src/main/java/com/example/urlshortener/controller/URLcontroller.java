package com.example.urlshortener.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.urlUpdateDTO;
import com.example.urlshortener.service.urlService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin
@RequestMapping("/url")
public class URLcontroller {
	@Autowired
	private urlService urlservice;
	
	
	@PostMapping
	public ResponseEntity<urlDTO> createRedirect(Authentication auth,  @RequestBody urlDTO urldto){
		String username = auth.getName();
		return new ResponseEntity<urlDTO>(urlservice.createRedirect(username, urldto), HttpStatus.OK);
		
	}
	
	@GetMapping("/{alias}")
	public ResponseEntity<?> HandleRedirect(Authentication auth, @PathVariable String alias) throws URISyntaxException{
		String username = auth.getName();
		urlDTO urldto = urlservice.getRedirect(username, alias);
		URI uri = new URI(urldto.getUrl());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		
	}
	@Operation(description = "Displays all the URL's of the user" )
	@GetMapping
	public ResponseEntity<?> viewRedirects(Authentication auth){
		String username = auth.getName();
		return new ResponseEntity<>(urlservice.Userurls(username), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateRedirect(Authentication auth, @RequestBody urlUpdateDTO urldto){
		String username = auth.getName();
		urlservice.updateRedirect(username, urldto);
		return new ResponseEntity<String>("URL Successfully updated", HttpStatus.OK);
	}
	
	@DeleteMapping("/{alias}")
	public ResponseEntity<String> DeleteRedirect(Authentication auth,@PathVariable String alias){
		String username = auth.getName();
		urlservice.deleteRedirect(username,alias);
		return new ResponseEntity<String>("Alias Successfully deleted", HttpStatus.OK);
	}

}
