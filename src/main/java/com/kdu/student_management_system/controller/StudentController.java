package com.kdu.student_management_system.controller;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

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

    @PostMapping
    public ResponseEntity<Map<String, String>> addStudent(@RequestBody Student student) {
        try {
            studentService.saveStudent(student);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Student added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error adding student: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to add student: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

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
        existingStudent.setCoursesEnrolled(updatedStudent.getCoursesEnrolled());

        try {
            studentService.saveStudent(existingStudent);
            return ResponseEntity.ok(existingStudent);
        } catch (Exception e) {
            logger.error("Error updating student: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}