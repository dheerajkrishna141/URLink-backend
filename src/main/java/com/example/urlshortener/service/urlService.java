package com.example.urlshortener.service;


import org.springframework.data.domain.Page;

import com.example.urlshortener.entity.Url;
import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.urlUpdateDTO;

public interface urlService {

	public urlDTO createRedirect(String username, urlDTO urldto);
	public urlDTO getRedirect(String username, String alias);
	public void deleteRedirect(String username, String alias);
	public void updateRedirect(String username, urlUpdateDTO urlDto);
	public Page<Url> Userurls(String username);
}
