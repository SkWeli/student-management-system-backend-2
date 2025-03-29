package com.kdu.student_management_system.service;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Generate a unique studentNumber in the format "XXXXXXXX" (e.g., "00001541")
    public String generateStudentNumber() {
        String lastStudentNumber = studentRepository.findAll()
                .stream()
                .map(Student::getStudentNumber)
                .filter(num -> num != null && num.matches("\\d+"))
                .max(String::compareTo)
                .orElse("00001540"); // Starting point if no students exist
        int newNumber = Integer.parseInt(lastStudentNumber) + 1;
        return String.format("%08d", newNumber); // Format as 8-digit number (e.g., "00001541")
    }

    // Save a student with an auto-generated studentNumber
    public Student saveStudent(Student student) {
        // Generate studentNumber for new students
        if (student.getStudentNumber() == null || student.getStudentNumber().isEmpty()) {
            String newStudentNumber = generateStudentNumber();
            // Ensure the generated studentNumber is unique
            while (studentRepository.findByStudentNumber(newStudentNumber) != null) {
                newStudentNumber = generateStudentNumber();
            }
            student.setStudentNumber(newStudentNumber);
        }
        return studentRepository.save(student);
    }
}