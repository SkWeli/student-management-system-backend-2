package com.kdu.student_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entity class representing admin users in the system
// + Maps to 'admins' table in database
// + Contains authentication and authorization fields
@Entity
@Table(name = "admins")
public class Admin {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    // Unique email identifier for admin
    private String email; 

    // Encrypted password
    private String password;

    private String role;

    // Constructors
    public Admin() {}

    public Admin(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
     // Getter for password (hashed)
    public String getPassword() {
        return password;
    }
    // Setter for password (should be hashed before setting)
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}