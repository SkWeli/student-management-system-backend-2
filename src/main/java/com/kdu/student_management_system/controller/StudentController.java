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
        try {
            studentService.saveStudent(student);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Student added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to add student: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Update a student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        // Update fields
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
        return ResponseEntity.noContent().build(); // 204 No Content on success
    }
}