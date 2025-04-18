package com.kdu.student_management_system.controller;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.model.AdminLog;
import com.kdu.student_management_system.repository.AdminLogRepository;
import com.kdu.student_management_system.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controller for handling all student-related operations
// + Manages CRUD operations for student records
// + Tracks admin actions through AdminLog
// + Secured endpoints for student data management
@RestController
@RequestMapping("/api/students")
public class StudentController {

    // Logger for tracking operations and errors
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminLogRepository adminLogRepository;
    // Method to get current admin's email
    private String getAdminEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "unknown_admin";
    }

    // Get all students endpoint
    // + Returns complete list of students
    // + No request parameters needed
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get single student by ID endpoint
    // + Returns 404 if student not found
    // + Logs successful and failed attempts
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            if (student == null) {
                logger.warn("Student not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Returning student: {}", student);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            logger.error("Error fetching student with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    // Creates new student 
    // + Handles duplicate student ID conflicts
    // + Logs admin activity
    // + Returns success/error messages
    @PostMapping
    public ResponseEntity<Map<String, String>> addStudent(@RequestBody Student student) {
        try {
            studentService.saveStudent(student);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Student added successfully");
            logger.info("Added student: {}", student);

            String adminEmail = getAdminEmail();
            AdminLog log = new AdminLog(adminEmail, "ADD_STUDENT", student.getStudentId(), "Added student: " + student.getFirstName() + " " + student.getLastName());
            adminLogRepository.save(log);

            return ResponseEntity.ok(response);

        } catch (DataIntegrityViolationException e) {
            logger.error("Duplicate student_id: {}", student.getStudentId(), e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "Student ID '" + student.getStudentId() + "' is already registered.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            logger.error("Error adding student: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to add student: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Update existing student (edit)
    // + Requires complete student object
    // + Validates student exists before update
    // + Logs admin activity
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setCurrentAddress(updatedStudent.getCurrentAddress());
        existingStudent.setBirthday(updatedStudent.getBirthday());
        existingStudent.setIdNumber(updatedStudent.getIdNumber());
        existingStudent.setDegree(updatedStudent.getDegree());
        existingStudent.setStudentId(updatedStudent.getStudentId());
        existingStudent.setYear(updatedStudent.getYear());
        existingStudent.setSemester(updatedStudent.getSemester());
        existingStudent.setCoursesEnrolled(updatedStudent.getCoursesEnrolled());

        try {
            studentService.saveStudent(existingStudent);
            logger.info("Updated student: {}", existingStudent);

            String adminEmail = getAdminEmail();
            AdminLog log = new AdminLog(adminEmail, "EDIT_STUDENT", existingStudent.getStudentId(), "Edited student details");
            adminLogRepository.save(log);

            return ResponseEntity.ok(existingStudent);
        } catch (Exception e) {
            logger.error("Error updating student: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete student 
    // + Returns 204 No Content on success
    // + Logs admin activity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        logger.info("Deleted student with id: {}", id);

        // Log admin action
        String adminEmail = getAdminEmail();
        AdminLog log = new AdminLog(adminEmail, "DELETE_STUDENT", existingStudent.getStudentId(), "Deleted student: " + existingStudent.getFirstName() + " " + existingStudent.getLastName());
        adminLogRepository.save(log);

        return ResponseEntity.noContent().build();
    }
}