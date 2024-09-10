package com.example.urlshortener.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> cors.disable());
		http.csrf(csrf -> csrf.disable());

		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/").permitAll();
			auth.requestMatchers("/user/register").permitAll();
			auth.anyRequest().authenticated();
		});
		
		http.httpBasic(Customizer.withDefaults()).formLogin(login->{
			login.defaultSuccessUrl("/user/login");
		});
		http.logout(logout->{
			logout.logoutSuccessUrl("/login?logout=true");
			logout.invalidateHttpSession(true);
		});
		http.sessionManagement(session->{
			session.maximumSessions(1);
			session.invalidSessionUrl("/login");
		});
		return http.build();
	}

}
