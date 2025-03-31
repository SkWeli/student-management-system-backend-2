package com.kdu.student_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Student Management System application
 * 
 * Key Features:
 * - Bootstraps the Spring Boot application
 * - Enables auto-configuration and component scanning
 * - Starts embedded web server
 * 
 * Configuration:
 * - @SpringBootApplication combines:
 *   + @Configuration - Marks as configuration class
 *   + @EnableAutoConfiguration - Enables Spring Boot auto-configuration  
 *   + @ComponentScan - Scans for components in current package and subpackages
 */
@SpringBootApplication
public class StudentManagementSystemApplication {

	/**
     * Main method that launches the Spring Boot application
     */
	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}

}
