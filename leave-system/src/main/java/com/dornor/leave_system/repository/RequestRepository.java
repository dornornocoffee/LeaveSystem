package com.dornor.leave_system.repository;

import com.dornor.leave_system.entity.Leave_request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Leave_request, Integer> {
}
