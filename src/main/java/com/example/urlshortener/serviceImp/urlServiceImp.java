package com.example.urlshortener.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.urlshortener.Exceptions.AliasNotFoundException;
import com.example.urlshortener.Exceptions.AliasNotUniqueException;
import com.example.urlshortener.Exceptions.UrlNotUniqueException;
import com.example.urlshortener.Exceptions.UserNotFoundException;
import com.example.urlshortener.Repository.UrlRepository;
import com.example.urlshortener.Repository.UserRepository;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.entity.User;
import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.urlUpdateDTO;
import com.example.urlshortener.service.urlService;

@Service
public class urlServiceImp implements urlService {
	@Autowired
	private UrlRepository urlRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public urlDTO createRedirect(String username, urlDTO urldto) {
		User user = userRepo.findByUsername(username);
		List<Url> lit = urlRepo.findAllByUsersId(user.getId());
		urldto.setUrl(urldto.getUrl().trim());
		for (Url a : lit) {
			if (a.getAlias().equals(urldto.getAlias())) {
				throw new AliasNotUniqueException("Alias: " + urldto.getAlias() + " is not unique");
			} else if (a.getUrl().equals(urldto.getUrl())) {
				throw new UrlNotUniqueException("URL already exists");
			}
		}
		Url url = urlRepo.save(modelmapper.map(urldto, Url.class));
		url.setUsers(user);
		Url savedurl = urlRepo.save(url);
		return modelmapper.map(savedurl, urlDTO.class);
	}

	@Override
	public urlDTO getRedirect(String username, String alias) {
		User user = userRepo.findByUsername(username);
		List<Url> lit = urlRepo.findAllByUsersId(user.getId());
		Url url = null;
		for (Url a : lit) {
			if (a.getAlias().equals(alias)) {
				url = a;
				break;
			}
		}
		if (url == null) {
			throw new AliasNotUniqueException("Alias: " + alias + " is not found!");
		}

		return modelmapper.map(url, urlDTO.class);
	}

	@Override
	public void deleteRedirect(String username, String alias) {
		User user = userRepo.findByUsername(username);
		List<Url> lit = urlRepo.findAllByUsersId(user.getId());
		Url url = null;
		for (Url a : lit) {
			if (a.getAlias().equals(alias)) {
				url = a;
				break;
			}
		}
		if (url == null) {
			throw new AliasNotUniqueException("Alias: " + alias + " is not found!");
		}
		urlRepo.delete(url);
		return;
	}

	@Override
	public Page<Url> Userurls(String username, Integer pageNo) {

		User user = userRepo.findByUsername(username);
		Pageable page = PageRequest.of(pageNo, 5);

		Page<Url> lit = urlRepo.findAllByUsers(page, user);
		return lit;
	}

	@Override
	public void updateRedirect(String username, urlUpdateDTO urlDto) {
		User user = userRepo.findByUsername(username);
		List<Url> urlList = urlRepo.findAllByUsersId(user.getId());
		Url a = null;
		for (Url url : urlList) {
			if (url.getAlias().equals(urlDto.getAlias())) {
				for (Url url_c : urlList) {
					if (url_c.getUrl().equals(urlDto.getNewUrl())) {
						throw new UrlNotUniqueException("Entered new URL already exists!");
					}
				}
				url.setUrl(urlDto.getNewUrl());
				a = url;
				break;
			}
		}
		if (a != null) {
			urlRepo.save(a);
		} else
			throw new AliasNotFoundException("Url with Alias:" + urlDto.getAlias() + " not found");

	}

}
