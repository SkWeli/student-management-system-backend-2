package com.kdu.student_management_system.repository;

import com.kdu.student_management_system.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email); // Changed from findByUsername
}