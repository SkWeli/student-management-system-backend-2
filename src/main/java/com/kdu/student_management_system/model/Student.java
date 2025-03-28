package com.kdu.student_management_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String studentId; // User-provided ID (e.g., "058644")

    @Column(nullable = false, unique = true)
    private String internalId; // Auto-generated ID (e.g., "0001", "0002")

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String currentAddress;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String degree;

    @ElementCollection
    private List<String> courses;

    // Constructors
    public Student() {}

    public Student(String studentId, String internalId, String firstName, String lastName, String currentAddress, LocalDate birthday, String degree, List<String> courses) {
        this.studentId = studentId;
        this.internalId = internalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.currentAddress = currentAddress;
        this.birthday = birthday;
        this.degree = degree;
        this.courses = courses;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId; 
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

}
