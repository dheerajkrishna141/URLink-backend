package com.example.urlshortener.payload;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class userDTO {

	private long id;
	@NotNull
	private String userName;
	@NotNull
	private String firstName;
	
	private String lastName;
	@NotNull
	private String password;
	@NotNull
	private Set<String> role;
	
	
}
