package com.smoothstack.utopia.counter.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	// Allow these origins on all routes.
        registry.addMapping("/**").allowedOrigins("http://utopia-airlines.s3-website-us-east-1.amazonaws.com",
        		"http://localhost:4200");
    }
}