package com.example.urlshortener.service;


import java.util.List;

import com.example.urlshortener.payload.urlDTO;
import com.example.urlshortener.payload.urlUpdateDTO;

public interface urlService {

	public urlDTO createRedirect(Long userid, urlDTO urldto);
	public urlDTO getRedirect(long userid, String alias);
	public void deleteRedirect(long userid, String alias);
	public void updateRedirect(long userid, urlUpdateDTO urlDto);
	public List<urlDTO> Userurls(long userid);
}
