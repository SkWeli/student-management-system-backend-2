package com.kdu.student_management_system.controller;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.service.StudentService;
import com.kdu.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by studentId (user-provided ID)
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable String studentId) {
        Student student = studentRepository.findByStudentId(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    // Create a new student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    // Update a student
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable String studentId, @RequestBody Student studentDetails) {
        Student student = studentRepository.findByStudentId(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setCurrentAddress(studentDetails.getCurrentAddress());
        student.setBirthday(studentDetails.getBirthday());
        student.setDegree(studentDetails.getDegree());
        student.setCourses(studentDetails.getCourses());
        // Do not update studentId or internalId
        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    // Delete a student
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId) {
        Student student = studentRepository.findByStudentId(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.delete(student);
        return ResponseEntity.ok("Student deleted successfully");
    }
    
}
