package com.kdu.student_management_system.controller;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.repository.StudentRepository;
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
    private StudentRepository studentRepository;

    // Fetch all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Add a new student
    @PostMapping
    public ResponseEntity<Map<String, String>> addStudent(@RequestBody Student student) {
        // Generate a student number (e.g., increment based on the last student)
        String lastStudentNumber = studentRepository.findAll()
                .stream()
                .map(Student::getStudentNumber)
                .filter(num -> num != null && num.matches("\\d+"))
                .max(String::compareTo)
                .orElse("00001540"); // Starting point if no students exist
        int newNumber = Integer.parseInt(lastStudentNumber) + 1;
        student.setStudentNumber(String.format("%08d", newNumber));

        studentRepository.save(student);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Student added successfully");
        return ResponseEntity.ok(response);
    }
}