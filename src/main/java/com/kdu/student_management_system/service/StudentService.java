package com.kdu.student_management_system.service;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Generate a unique studentId in the format "XXXXXX" (e.g., "054760")
    public String generateStudentId() {
        String lastStudentId = studentRepository.findAll()
                .stream()
                .map(Student::getStudentId)
                .filter(id -> id != null && id.matches("\\d+"))
                .max(String::compareTo)
                .orElse("054759"); // Starting point if no students exist (one less than 054760)
        int newId = Integer.parseInt(lastStudentId) + 1;
        return String.format("%06d", newId); // Format as 6-digit number (e.g., "054760")
    }

    // Save a student with an auto-generated studentId if not provided
    public Student saveStudent(Student student) {
        // Generate studentId for new students if not provided
        if (student.getStudentId() == null || student.getStudentId().isEmpty()) {
            String newStudentId = generateStudentId();
            // Ensure the generated studentId is unique
            while (studentRepository.findByStudentId(newStudentId) != null) {
                newStudentId = generateStudentId();
            }
            student.setStudentId(newStudentId);
        }
        return studentRepository.save(student);
    }

    // Fetch all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Fetch a student by studentId
    public Student getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}