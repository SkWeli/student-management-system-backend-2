package com.kdu.student_management_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Admin activity logs
// + Tracks all administrative actions in the system
// + Maps to 'admin_logs' table in database
@Entity
@Table(name = "admin_logs")
public class AdminLog {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Email of admin who performed the action
    @Column(name = "admin_email", nullable = false)
    private String adminEmail;

    // Type of action(EDIT/DELETE/ADD)
    @Column(name = "action", nullable = false)
    private String action;

    // Student ID affected by the action
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "details")
    private String details;

    // Constructors
    // + Initializes timestamp to current time
    public AdminLog() {
        this.timestamp = LocalDateTime.now();
    }

    // Parameterized constructor for creating logs
    public AdminLog(String adminEmail, String action, String studentId, String details) {
        this.adminEmail = adminEmail;
        this.action = action;
        this.studentId = studentId;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getAdminEmail() { 
        return adminEmail; 
    }

    public void setAdminEmail(String adminEmail) { 
        this.adminEmail = adminEmail; 
    }

    public String getAction() { 
        return action; 
    }

    public void setAction(String action) { 
        this.action = action; 
    }

    public String getStudentId() { 
        return studentId; 
    }

    public void setStudentId(String studentId) { 
        this.studentId = studentId; 
    }

    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }

    public void setTimestamp(LocalDateTime timestamp) { 
        this.timestamp = timestamp; 
    }

    public String getDetails() { 
        return details; 
    }

    public void setDetails(String details) { 
        this.details = details; 
    }
}