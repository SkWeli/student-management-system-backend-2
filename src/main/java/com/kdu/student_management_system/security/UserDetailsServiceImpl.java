package com.kdu.student_management_system.security;

import com.kdu.student_management_system.repository.AdminRepository;
import com.kdu.student_management_system.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

/**
 * Custom UserDetailsService implementation for admin authentication
 * 
 * Responsibilities:
 * + Loads admin details from database during authentication
 * + Bridges between Admin entity and Spring Security's UserDetails
 * + Throws UsernameNotFoundException for invalid credentials
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Loads admin user by email (username)
     * 
     * @param admin's email address (used as username)
     * @return UserDetails object containing admin credentials and role
     * @throws UsernameNotFoundException if admin not found
     * 
     * Process Flow:
     * 1. Queries AdminRepository for email
     * 2. If found, builds UserDetails with:
     *    - Email as username
     *    - Encoded password
     *    - "ADMIN" role (converted to ROLE_ADMIN)
     * 3. If not found, throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

        return User.withUsername(admin.getEmail())
                .password(admin.getPassword())
                .roles("ADMIN") // Assigns "ROLE_ADMIN" internally
                .build();
    }
}