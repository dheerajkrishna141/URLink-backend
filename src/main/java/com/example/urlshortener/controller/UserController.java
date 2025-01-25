package com.example.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.entity.User;
import com.example.urlshortener.payload.loginMessage;
import com.example.urlshortener.payload.passwordDTO;
import com.example.urlshortener.payload.userDTO;
import com.example.urlshortener.service.userService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs")
public class UserController {
	@Autowired
	private userService userService;

	@Operation(summary = "Register a new user", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = userDTO.class))))
	@ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class)))
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody @Valid userDTO userdto) {
		try {
			return new ResponseEntity<>(userService.createUser(userdto), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Operation(summary = "Login the current user", description = "Logging in the current user using the basicAuth header", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = loginMessage.class)))
	@GetMapping("/login")
	public ResponseEntity<?> loginUser(Authentication auth) {

		return new ResponseEntity<loginMessage>(userService.loginUser(auth.getName()), HttpStatus.OK);
	}

	@Operation(summary = "Delete user with userId", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.ALL_VALUE, schema = @Schema(implementation = String.class)))
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable long id) {
		
		return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.OK);
	}

	@Operation(summary = "Update current user's password", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponse(responseCode = "202", description = "Accepted", content = @Content(mediaType = MediaType.ALL_VALUE, schema = @Schema(implementation = Boolean.class)))
	@PostMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(Authentication auth, @RequestBody passwordDTO password) {

		return new ResponseEntity<>(userService.changePassword(auth.getName(), password), HttpStatus.ACCEPTED);

	}

}
