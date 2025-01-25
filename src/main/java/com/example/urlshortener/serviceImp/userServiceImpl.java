package com.example.urlshortener.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.urlshortener.Exceptions.UserNotFoundException;
import com.example.urlshortener.Exceptions.UserNotUniqueException;
import com.example.urlshortener.Repository.RoleRepository;
import com.example.urlshortener.Repository.UrlRepository;
import com.example.urlshortener.Repository.UserRepository;
import com.example.urlshortener.entity.Roles;
import com.example.urlshortener.entity.User;
import com.example.urlshortener.payload.loginMessage;
import com.example.urlshortener.payload.passwordDTO;
import com.example.urlshortener.payload.userDTO;
import com.example.urlshortener.service.EncryptionService;
import com.example.urlshortener.service.userService;

@Service
public class userServiceImpl implements userService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UrlRepository urlRepo;

	@Autowired
	EncryptionService encrypt;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	ModelMapper modelmapper;

	@Override
	public User createUser(userDTO userdto) {
		User user = userRepository.findByUsername(userdto.getUserName());
		if (user != null)
			throw new UserNotUniqueException("Username already exisits!");
		user = modelmapper.map(userdto, User.class);
		user.setPassword(encrypt.EncryptPassword(userdto.getPassword()));

		for (String name : userdto.getRole()) {

			Roles role = roleRepo.findByName(name);
			user.getRoles().add(role);

		}
		user = userRepository.save(user);

		return user;
	}

	@Override
	public String deleteUser(long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with Id: " + id + " is not found!"));
		userRepository.delete(user);

		return "User with ID: " + id + " has been deleted succesfully.";
	}

	@Override
	public loginMessage loginUser(String username) {

		User user = userRepository.findByUsername(username);

		return new loginMessage("Login Success", true, user);

	}

	@Override
	public boolean changePassword(String username, passwordDTO password) {
		try {
			User user = userRepository.findByUsername(username);
			String encryptPass = encrypt.EncryptPassword(password.getPassword());
			user.setPassword(encryptPass);
			userRepository.save(user);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
