package com.kdu.student_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS) settings.
 * 
 * +This class defines global CORS policies for the application to allow
 *  secure communication between the backend API and frontend clients.</p>
 * 
 * *Key Features:
 * 
 *   -Global CORS configuration applied to all endpoints
 *   -Specific origin allowance for development (localhost:3000)
 *   -Support for common HTTP methods
 *   -Header and credential support
 * 
 */

@Configuration
public class CORSConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS for all endpoints
                        .allowedOrigins("http://localhost:3000") // Allow your frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow cookies, if needed
            }
        };
    }
}