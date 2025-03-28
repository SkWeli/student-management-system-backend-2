package com.kdu.student_management_system.service;

import com.kdu.student_management_system.model.Student;
import com.kdu.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Generate a unique internalId in the format "XXXX" (e.g., "0001", "0002")
    public String generateInternalId() {
        long studentCount = studentRepository.count();
        long nextId = studentCount + 1;
        return String.format("%04d", nextId); // Format as 4-digit number (e.g., "0001")
    }

    // Save a student with an auto-generated internalId
    public Student saveStudent(Student student) {
        // Generate internalId for new students
        if (student.getInternalId() == null || student.getInternalId().isEmpty()) {
            String newInternalId = generateInternalId();
            // Ensure the generated internalId is unique
            while (studentRepository.findByInternalId(newInternalId) != null) {
                newInternalId = generateInternalId();
            }
            student.setInternalId(newInternalId);
        }
        return studentRepository.save(student);
    }
}