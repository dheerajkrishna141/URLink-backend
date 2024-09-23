package com.example.urlshortener.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.urlshortener.Repository.UserRepository;
import com.example.urlshortener.entity.User;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		User user = userRepo.findByUsername(username);

		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				return new UsernamePasswordAuthenticationToken(username, password, user.getRoles());
			} else {
				throw new BadCredentialsException("Invalid Password!");
			}
		} else {
			throw new UsernameNotFoundException("User with username: " + username + " not found!");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
