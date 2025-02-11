package com.dornor.leave_system.repository;

import com.dornor.leave_system.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<LeaveRequest, Integer> {
}
