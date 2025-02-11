package com.dornor.leave_system.repository;

import com.dornor.leave_system.entity.Leave_types;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Leave_types, Integer> {
}
