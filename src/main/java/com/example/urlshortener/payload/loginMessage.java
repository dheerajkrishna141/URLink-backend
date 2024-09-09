package com.example.urlshortener.payload;

import com.example.urlshortener.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class loginMessage {

	private String message;
	private boolean status;
	private User user;
}
