package com.example.urlshortener.service;

import com.example.urlshortener.entity.User;
import com.example.urlshortener.payload.loginMessage;
import com.example.urlshortener.payload.passwordDTO;
import com.example.urlshortener.payload.userDTO;


public interface userService {
	
	public User createUser(userDTO userdto);
	public String deleteUser(long id);
	public loginMessage loginUser(String string);
	public boolean changePassword(String username, passwordDTO password);

}
