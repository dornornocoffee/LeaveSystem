package com.dornor.leave_system.repository;

import com.dornor.leave_system.entity.LeaveTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<LeaveTypes, Long> {
}
