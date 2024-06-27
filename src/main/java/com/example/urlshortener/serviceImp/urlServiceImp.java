package com.example.urlshortener.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.urlshortener.Exceptions.AliasNotFoundException;
import com.example.urlshortener.Exceptions.AliasNotUniqueException;
import com.example.urlshortener.Exceptions.UrlNotUniqueException;
import com.example.urlshortener.Exceptions.UserNotFoundException;
import com.example.urlshortener.Repository.UrlRepository;
import com.example.urlshortener.Repository.UserRepository;
import com.example.urlshortener.entity.User;
import com.example.urlshortener.entity.urlLoader;
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
	public urlDTO createRedirect(Long userid, urlDTO urldto) {
		User user = userRepo.findById(userid)
				.orElseThrow(() -> new UserNotFoundException("User with ID:" + userid + " is not found!"));
		List<urlLoader> lit = urlRepo.findAllByUsersId(userid);
		urldto.setUrl(urldto.getUrl().trim());
		for (urlLoader a : lit) {
			if (a.getAlias().equals(urldto.getAlias())) {
				throw new AliasNotUniqueException("Alias with user Id: " + userid + " is not unique");
			} else if(a.getUrl().equals(urldto.getUrl())){
				throw new UrlNotUniqueException("URL already exists");
			}
		}
		urlLoader url = urlRepo.save(modelmapper.map(urldto, urlLoader.class));
		url.setUsers(user);
		urlLoader savedurl = urlRepo.save(url);
		return modelmapper.map(savedurl, urlDTO.class);
	}

	@Override
	public urlDTO getRedirect(long userid, String alias) {
		User user = userRepo.findById(userid)
				.orElseThrow(() -> new UserNotFoundException("User with ID:" + userid + " is not found!"));
		List<urlLoader> lit = urlRepo.findAllByUsersId(userid);
		urlLoader url=null;
		for(urlLoader a: lit) {
			if(a.getAlias().equals(alias)) {
				url=a;
				break;
			}
		}if(url==null) {
			throw new AliasNotUniqueException("Alias: "+alias+" is not found with user Id: "+userid);
		}
		
		return modelmapper.map(url, urlDTO.class);
	}

	@Override
	public void deleteRedirect(long userid,String alias) {
		User user = userRepo.findById(userid)
				.orElseThrow(() -> new UserNotFoundException("User with ID:" + userid + " is not found!"));
		List<urlLoader> lit = urlRepo.findAllByUsersId(userid);
		urlLoader url=null;
		for(urlLoader a: lit) {
			if(a.getAlias().equals(alias)) {
				url=a;
				break;
			}
		}if(url==null) {
			throw new AliasNotUniqueException("Alias: "+alias+" is not found with user Id: "+userid);
		}
		urlRepo.delete(url);
		return;
	}
	

	@Override
	public List<urlDTO> Userurls(long userid) {
		
		User user = userRepo.findById(userid).orElseThrow(() -> new UserNotFoundException("User with ID:" + userid + " is not found!"));

		List<urlLoader> lit = urlRepo.findAllByUsersId(user.getId());
	
		List<urlDTO> list = new ArrayList<>();
		for(urlLoader a: lit) {
			urlDTO b = new urlDTO();
			b.setAlias(a.getAlias());
			b.setUrl(a.getUrl());
			list.add(b);
		}
		return list;
	}

	@Override
	public void updateRedirect(long userid, urlUpdateDTO urlDto) {
		User user = userRepo.findById(userid).orElseThrow(() -> new UserNotFoundException("User with ID:" + userid + " is not found!"));
		List<urlLoader> urlList = urlRepo.findAllByUsersId(userid);
		urlLoader a =null;
		for(urlLoader url: urlList) {
			if(url.getAlias().equals(urlDto.getAlias())) {
				for(urlLoader url_c: urlList) {
					if(url_c.getUrl().equals(urlDto.getNewUrl())) {
						throw new UrlNotUniqueException("Entered new URL already exists!");
					}
				}
				url.setUrl(urlDto.getNewUrl());
				a=url;
				break;
			}
		}
		if(a!= null) {
			urlRepo.save(a);
		}else throw new AliasNotFoundException("Url with Alias:"+urlDto.getAlias()+" not found");
			
		
	}


}
