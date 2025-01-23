package com.example.urlshortener.Configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class WebSecurityConfig {

	@Autowired
	AuthenticationEntryPoint authEntry;

	@Autowired
	CustomLogoutHandler logoutHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());
		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOriginPatterns(Collections.singletonList("*"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowCredentials(true);
			return config;
		}));
//		http.cors(cors->cors.disable());

		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/swagger-ui/**").permitAll();
			auth.requestMatchers("/").permitAll();
			auth.requestMatchers("/docs").permitAll();
			auth.requestMatchers("/v3/**").permitAll();
			auth.requestMatchers("/user/register").permitAll();
			auth.requestMatchers("/actuator/**").permitAll();
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
			session.maximumSessions(2);
			session.invalidSessionUrl("/login");

		});
		return http.build();
	}

}
