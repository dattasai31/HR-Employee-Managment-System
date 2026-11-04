package com.example.demo.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfiguration {
	@Bean
	public OpenAPI configOpenAPI()
	{
		return new OpenAPI().info(new Info().title("HR EMPLOYEE MANAGMENT SYSTEM").version("2.12").description("Managing employee data"));
				
	}

}
