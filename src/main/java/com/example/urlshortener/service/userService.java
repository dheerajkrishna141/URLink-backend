package com.example.urlshortener.service;

import com.example.urlshortener.entity.User;
import com.example.urlshortener.payload.loginMessage;
import com.example.urlshortener.payload.userDTO;


public interface userService {
	
	public User createUser(userDTO userdto);
	public void deleteUser(long id);
	public loginMessage loginUser(String string);

}
