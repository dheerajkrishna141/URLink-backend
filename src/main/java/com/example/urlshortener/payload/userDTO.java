package com.example.urlshortener.payload;

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
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	
	
}
