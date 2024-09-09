package com.example.urlshortener.serviceImp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.urlshortener.Exceptions.UserNotFoundException;
import com.example.urlshortener.Exceptions.UserNotUniqueException;
import com.example.urlshortener.Repository.UrlRepository;
import com.example.urlshortener.Repository.UserRepository;
import com.example.urlshortener.entity.User;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.payload.loginMessage;
import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.userDTO;
import com.example.urlshortener.service.userService;
@Service
public class userServiceImpl implements userService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UrlRepository urlRepo;
	
	@Autowired
	ModelMapper modelmapper;
	
	@Override
	public userDTO createUser(userDTO userdto) {
		User user = userRepository.findByUsername(userdto.getUserName());
		if(user != null) throw new UserNotUniqueException("Username already exisits!");
		
		user = modelmapper.map(userdto, User.class);
		user = userRepository.save(user);
		
		
		return modelmapper.map(user, userDTO.class);
	}

	@Override
	public void deleteUser(long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with Id: "+id+" is not found!"));
		userRepository.delete(user);
		
		return ;
	}

	@Override
	public loginMessage loginUser(userDTO userdto) {
		
		User user = userRepository.findByUsername(userdto.getUserName());
		if(user != null) {
			boolean isPwdRight = user.getPassword().equals(userdto.getPassword());
			if(isPwdRight) {
				return new loginMessage("Login Success", true, user);
			}else {
				return new loginMessage("Password is incorrect", false, user);
			}
		}
		else {
			 throw new UserNotFoundException("User does not exsits!");
		}
		
	}


}
