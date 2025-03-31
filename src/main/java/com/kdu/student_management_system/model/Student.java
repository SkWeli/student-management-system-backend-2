package com.kdu.student_management_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
// Entity class representing student records
// + Maps to 'students' table in database
// + Contains all student academic and personal information

@Entity
@Table(name = "students")
public class Student {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique student identifier
    @Column(name = "student_id", unique = true)
    private String studentId; 

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "degree")
    private String degree;

    @Column(name = "year", nullable = false) 
    private Integer year;

    @Column(name = "semester", nullable = false) 
    private Integer semester;

    // List of enrolled courses
    // + Stored in separate table 'student_courses'
    // + Eagerly fetched with student data
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "course")
    private List<String> coursesEnrolled = new ArrayList<>();

    // Constructors
    public Student() {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    // Custom getter for serialization
    public List<String> getCoursesEnrolled() {
        return coursesEnrolled != null ? new ArrayList<>(coursesEnrolled) : new ArrayList<>();
    }

    public void setCoursesEnrolled(List<String> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled != null ? new ArrayList<>(coursesEnrolled) : new ArrayList<>();
    }
}