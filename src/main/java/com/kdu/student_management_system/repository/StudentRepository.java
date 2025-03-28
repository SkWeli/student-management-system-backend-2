package com.kdu.student_management_system.repository;

import com.kdu.student_management_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentId(String studentId);
    Student findByInternalId(String internalId); // New method to find by internalId
}