package com.example.urlshortener.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.urlUpdateDTO;
import com.example.urlshortener.service.urlService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/url")
@Tag(name = "URL APIs")
@SecurityRequirement(name = "basicAuth")
public class URLcontroller {
	@Autowired
	private urlService urlservice;

	@Operation(summary = "Add a new URL", description = "Add a new URL with a unique alias")
	@ApiResponse(responseCode = "200", description = "OK",
    content = @Content(mediaType = MediaType.ALL_VALUE,
    schema = @Schema(implementation = String.class)))
	@PostMapping
	public ResponseEntity<?> createRedirect(Authentication auth, @RequestBody urlDTO urldto) {
		String username = auth.getName();
		return new ResponseEntity<String>(urlservice.createRedirect(username, urldto), HttpStatus.OK);

	}

	@Operation(summary = "Redirect the user to the original URL")
	@GetMapping("/{alias}")
	public ResponseEntity<?> HandleRedirect(Authentication auth, @PathVariable String alias) throws URISyntaxException {
		String username = auth.getName();
		urlDTO urldto = urlservice.getRedirect(username, alias);
		URI uri = new URI(urldto.getUrl());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

	}

	@Operation(summary = "Get all added URLs",description = "Displays all the URLs of the user in a paginated format")
	@ApiResponse(responseCode = "202", description = "Login successful",
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
    schema = @Schema(implementation = UrlPage.class)))
	@GetMapping
	public ResponseEntity<?> viewRedirects(Authentication auth, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize) {
		String username = auth.getName();
		return new ResponseEntity<>(urlservice.Userurls(username, pageNo, pageSize), HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Update URL", description = "Update the URL of an existing alias")
	@PostMapping("/update")
	public ResponseEntity<String> updateRedirect(Authentication auth, @RequestBody urlUpdateDTO urldto) {
		String username = auth.getName();
		urlservice.updateRedirect(username, urldto);
		return new ResponseEntity<String>("URL Successfully updated", HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Delete a URL", description = "Delete a specific URL with it's alias")
	@DeleteMapping
	public ResponseEntity<String> DeleteRedirect(Authentication auth, @RequestParam String alias) {
		String username = auth.getName();
		urlservice.deleteRedirect(username, alias);
		return new ResponseEntity<String>("Alias Successfully deleted", HttpStatus.OK);
	}
	
	@Schema(description = "Page of URLs")
	@Hidden
	abstract
	class UrlPage implements Page<urlDTO>{
		
	}

}
