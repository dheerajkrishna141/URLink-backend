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

import com.example.urlshortener.Exceptions.AliasNotFoundException;
import com.example.urlshortener.Exceptions.AliasNotUniqueException;
import com.example.urlshortener.Exceptions.UrlNotUniqueException;
import com.example.urlshortener.entity.User;
import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.urlUpdateDTO;
import com.example.urlshortener.payload.userDTO;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UrlServiceTests {

	@Autowired
	urlService urlService;

	@Autowired
	userService userService;

	private User testUser;

	@BeforeAll
	public void setUp() {
		testUser = userService.createUser(
				new userDTO("test1", "testFirst", "testLast", "testPass", Collections.singleton("ROLE_USER")));
		urlDTO dto = new urlDTO("testAlias", "https://test.com");
		urlService.createRedirect(testUser.getUsername(), dto);

	}

	@Test
	public void createRedirectWithDuplicateAliasTest() {

		urlDTO dto2 = new urlDTO("testAlias", "https://test2.com");

		Assertions.assertThrows(AliasNotUniqueException.class, () -> {
			urlService.createRedirect(testUser.getUsername(), dto2);
		});

	}

	@Test
	public void createRedirectWithDuplicateUrlTest() {
		urlDTO dto2 = new urlDTO("testAlias2", "https://test.com");
		Assertions.assertThrows(UrlNotUniqueException.class, () -> {
			urlService.createRedirect(testUser.getUsername(), dto2);
		});

	}

	@Test
	public void createLegitRedirectTest() {
		urlDTO dto2 = new urlDTO("testAlias2", "https://test2.com");
		Assertions.assertEquals("Successfully added!", urlService.createRedirect(testUser.getUsername(), dto2));

	}

	@Test
	public void getNonExistingRedirectTest() {
		Assertions.assertThrows(AliasNotFoundException.class, () -> {
			urlService.getRedirect(testUser.getUsername(), "NotExists");
		});
	}

	@Test
	public void getLegitRedirectTest() {
		urlDTO dto = new urlDTO("testAlias", "https://test.com");

		Assertions.assertEquals(dto.getUrl(), urlService.getRedirect(testUser.getUsername(), "testAlias").getUrl());
	}

	@Test
	public void deleteNonExistingRedirectTest() {
		Assertions.assertThrows(AliasNotFoundException.class, () -> {
			urlService.deleteRedirect(testUser.getUsername(), "NotExists");
		});
	}

	@Test
	public void deleteLegitRedirectTest() {
		urlDTO dto = new urlDTO("newTestAlias", "https://newTest.com");

		urlService.createRedirect(testUser.getUsername(), dto);
		urlService.deleteRedirect(testUser.getUsername(), "newTestAlias");

		Assertions.assertThrows(AliasNotFoundException.class, () -> {
			urlService.deleteRedirect(testUser.getUsername(), "newTestAlias");
		});
	}

	@Test
	public void updateToExistingRedirectTest() {
		urlUpdateDTO dto = new urlUpdateDTO("testAlias", "https://test.com");

		Assertions.assertThrows(UrlNotUniqueException.class, () -> {
			urlService.updateRedirect(testUser.getUsername(), dto);
		});
	}

	@Test
	public void updateRedirectWithNonExistingAlias() {
		urlUpdateDTO dto = new urlUpdateDTO("NonExistingAlias", "https://Test.com");

		Assertions.assertThrows(AliasNotFoundException.class, () -> {
			urlService.updateRedirect(testUser.getUsername(), dto);
		});
	}

	@Test
	public void updateLegitRedirect() {
		urlDTO dto = new urlDTO("newTestAlias", "https://newTest.com");
		urlUpdateDTO dto1 = new urlUpdateDTO("newTestAlias", "https://updatedTest.com");
		urlService.createRedirect(testUser.getUsername(), dto);
		urlService.updateRedirect(testUser.getUsername(), dto1);

		Assertions.assertEquals(dto1.getNewUrl(),
				urlService.getRedirect(testUser.getUsername(), dto1.getAlias()).getUrl());

	}

	@AfterAll
	public void undoSetUp() {
		userService.deleteUser(testUser.getId());
	}
}
