package com.kdu.student_management_system.controller;

import com.kdu.student_management_system.model.Admin;
import com.kdu.student_management_system.repository.AdminRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")


public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    
    private final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // Find admin by email
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail());
        if (admin == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.status(401).body(response);
        }

        // Verify password
        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.status(401).body(response);
        }

        // Generate JWT token
        String token = Jwts.builder()
                .setSubject(loginRequest.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        // Return the token in the expected format
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // For testing: Create a user (remove in production)
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody LoginRequest loginRequest) {
        if (adminRepository.findByEmail(loginRequest.getEmail()) != null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Email already exists");
            return ResponseEntity.status(400).body(response);
        }

        Admin user = new Admin();
        user.setEmail(loginRequest.getEmail());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        adminRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }
}

class LoginRequest {
    private String email;
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}