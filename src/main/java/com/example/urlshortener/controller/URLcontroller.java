package com.example.urlshortener.controller;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpHeaders;

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

import com.example.urlshortener.Repository.UrlRepository;
import com.example.urlshortener.entity.urlLoader;
import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.urlUpdateDTO;
import com.example.urlshortener.service.urlService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class URLcontroller {
	@Autowired
	private urlService urlservice;
	
	
	@PostMapping("/{userid}")
	public ResponseEntity<urlDTO> createRedirect(@PathVariable(name="userid") long userid,  @RequestBody urlDTO urldto){
		return new ResponseEntity<urlDTO>(urlservice.createRedirect(userid, urldto), HttpStatus.OK);
		
	}
	
	@GetMapping("/{userid}/{alias}")
	public ResponseEntity<?> HandleRedirect(@PathVariable(name="userid") long userid, @PathVariable(name="alias") String alias) throws URISyntaxException{
		urlDTO urldto = urlservice.getRedirect(userid, alias);
		URI uri = new URI(urldto.getUrl());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		
	}
	@GetMapping("/{userid}")
	public ResponseEntity<?> viewRedirects(@PathVariable(name="userid") long userid){
		return new ResponseEntity<>(urlservice.Userurls(userid), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/update/{userid}")
	public ResponseEntity<?> updateRedirect(@PathVariable(name="userid") long userid, @RequestBody urlUpdateDTO urldto){
		urlservice.updateRedirect(userid, urldto);
		return new ResponseEntity<String>("URL Successfully updated", HttpStatus.OK);
	}
	
	@DeleteMapping("/{userid}/{alias}")
	public ResponseEntity<String> DeleteRedirect(@PathVariable(name="userid") long userid,@PathVariable(name="alias") String alias){
		urlservice.deleteRedirect(userid,alias);
		return new ResponseEntity<String>("Alias Successfully deleted", HttpStatus.OK);
	}

}
