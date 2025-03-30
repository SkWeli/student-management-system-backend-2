package com.kdu.student_management_system.controller;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Fetch all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Fetch a student by id
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getAllStudents()
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    // Add a new student
    @PostMapping
    public ResponseEntity<Map<String, String>> addStudent(@RequestBody Student student) {
        studentService.saveStudent(student);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Student added successfully");
        return ResponseEntity.ok(response);
    }
}