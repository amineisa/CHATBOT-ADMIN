package com.chatbot.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@SpringBootApplication
public class ChatbotAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotAdminApplication.class, args);
	}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	registry.addMapping("/api/**")
        		.allowedOrigins("http://localhost:4200")
        		.allowedMethods("PUT", "DELETE","GET","POST")
        		.allowedHeaders("Access-Control-Allow-Origin")
        		;
        			//.allowedHeaders("header1", "header2", "header3")
        		//.exposedHeaders("header1", "header2")
        		//.allowCredentials(false).maxAge(3600);
            }
        };
    }
	
}
