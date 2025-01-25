package com.example.urlshortener.service;

import java.util.Collections;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.urlshortener.Exceptions.UserNotFoundException;
import com.example.urlshortener.Exceptions.UserNotUniqueException;
import com.example.urlshortener.Repository.UserRepository;
import com.example.urlshortener.entity.User;
import com.example.urlshortener.payload.passwordDTO;
import com.example.urlshortener.payload.userDTO;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTests {

	@Autowired
	private userService userUnderTest;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EncryptionService encrypt;

	private User tempUser;

	@BeforeAll
	public void setUp() {
		userDTO dto1 = new userDTO("test1", "kratos", "fury", "kratos", Collections.singleton("ROLE_USER"));
		tempUser = userUnderTest.createUser(dto1);
	}

	@Test
	public void createUserWithExistingUsernameTest() {
		userDTO dto2 = new userDTO("test1", "kratos1", "fury1", "kratos1", Collections.singleton("ROLE_USER"));
		Assertions.assertThrows(UserNotUniqueException.class, () -> {
			userUnderTest.createUser(dto2);
		});
	}

	@Test
	public void createNewUserTest() {
		userDTO dto = new userDTO("kratosNew", "kratos", "fury", "kratos", Collections.singleton("ROLE_USER"));
		User tempUser = userUnderTest.createUser(dto);
		Assertions.assertNotNull(userRepo.findByUsername(dto.getUserName()));
		userUnderTest.deleteUser(tempUser.getId());
	}

	@Test
	public void deleteNonExistingUserTest() {
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			userUnderTest.deleteUser(-1);
		});

	}

	@Test
	public void deleteLegitUserTest() {
		userDTO dto = new userDTO("kratosNew", "kratos", "fury", "kratos", Collections.singleton("ROLE_USER"));
		User tempUser = userUnderTest.createUser(dto);
		Assertions.assertEquals("User with ID: " + tempUser.getId() + " has been deleted succesfully.",
				userUnderTest.deleteUser(tempUser.getId()));

	}

	@Test
	public void changePasswordTest() {
		passwordDTO dto2 = new passwordDTO("newPassword");
		userUnderTest.changePassword(tempUser.getUsername(), dto2);
		tempUser = userRepo.findByUsername(tempUser.getUsername()); // getting the updated user
		Assertions.assertTrue(encrypt.VerifyPassword("newPassword", tempUser.getPassword()));

	}

	@AfterAll
	public void undoSetup() {
		userUnderTest.deleteUser(tempUser.getId());
	}

}
