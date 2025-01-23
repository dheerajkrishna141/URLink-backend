package com.example.urlshortener.Configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI myCustomConfig(){
		
		Components components = new Components();
		components.addSecuritySchemes(
		        "basicAuth", new SecurityScheme()
		        .type(SecurityScheme.Type.HTTP)
		        .scheme("basic")
		        .in(SecurityScheme.In.HEADER)
		        .name("Authorization"));
		return new OpenAPI().info(
				new Info().title("URLink App APIs").description("By Dheeraj Krishna")
				).tags(Arrays.asList(new Tag().name("URL APIs")))
				.components(components);

	}

}
