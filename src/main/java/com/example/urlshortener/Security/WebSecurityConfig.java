package com.example.urlshortener.Security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.example.urlshortener.Configuration.AuthenticationEntryPoint;
import com.example.urlshortener.Configuration.CustomLogoutHandler;

@Configuration
public class WebSecurityConfig {

	@Autowired
	AuthenticationEntryPoint authEntry;
	
	@Autowired
	CustomLogoutHandler logoutHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowCredentials(true);
			return config;
		}));
		http.csrf(csrf -> csrf.disable());

		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/").permitAll();
			auth.requestMatchers("/user/register").permitAll();
			auth.anyRequest().authenticated();

		});

		http.httpBasic(login -> {
			login.authenticationEntryPoint(authEntry);
		});

		http.logout(logout -> {
			logout.logoutSuccessUrl("/login?logout=true");
			logout.logoutSuccessHandler(logoutHandler);
			logout.invalidateHttpSession(true);
		});
		http.sessionManagement(session -> {
			session.maximumSessions(1);
			session.invalidSessionUrl("/login");
		});
		return http.build();
	}

}
