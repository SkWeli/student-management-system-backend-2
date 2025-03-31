package com.kdu.student_management_system.repository;

import com.kdu.student_management_system.model.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLogRepository extends JpaRepository<AdminLog, Long> {
}