package com.kdu.student_management_system.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT Authentication Filter
// + Intercepts incoming requests to validate JWT tokens
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Logger for tracking authentication flow
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // Service to load user details from database
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Secret key for JWT validation (must match token provider)
    private final String SECRET_KEY = "4C8dnoRvBUmzbGdrx8PxdQGs2PYZYBqXt5qpW2a0snXjMcK7oR1asaRvKtpJBcA5fFv6o60Fvqh2jA0XfIcf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f"; // Match JwtTokenProvider

    // Main filter method
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        logger.info("Request: " + request.getMethod() + " " + request.getRequestURI());
        logger.info("Token received: " + token);
        if (token != null) {
            try {
                // Extract email from JWT token
                String email = Jwts.parser()
                                    .setSigningKey(SECRET_KEY)
                                    .parseClaimsJws(token)
                                    .getBody()
                                    .getSubject();
                logger.info("Extracted email: " + email);
                // Load user details from database
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                logger.info("Authorities: " + userDetails.getAuthorities());
                // Create authentication token
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                        // Set additional request details
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // Set authentication in security context
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                
                        logger.info("Authentication set for: " + email);
            } catch (Exception e) {
                logger.error("Invalid JWT token: " + e.getMessage());
            }
        } else {
            logger.warn("No token found in request");
        }
        // Continue filter chain
        filterChain.doFilter(request, response);
    }


    // Extracts JWT token from Authorization header
    // + Expects format: "Bearer <token>"
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);// Remove "Bearer " prefix
        }
        return null;
    }
}