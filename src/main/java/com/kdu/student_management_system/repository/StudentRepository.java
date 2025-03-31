package com.kdu.student_management_system.repository;

import com.kdu.student_management_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Repository interface for Student entity
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentId(String studentId); // Updated from findByStudentNumber
}