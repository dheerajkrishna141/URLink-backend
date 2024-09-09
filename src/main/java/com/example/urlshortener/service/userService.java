package com.example.urlshortener.service;

import java.util.List;

import com.example.urlshortener.payload.loginMessage;
import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.userDTO;


public interface userService {
	
	public userDTO createUser(userDTO userdto);
	public void deleteUser(long id);
	public loginMessage loginUser(userDTO userdto);

}
