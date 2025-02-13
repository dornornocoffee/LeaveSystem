package com.dornor.leave_system.repository;

import com.dornor.leave_system.entity.LeaveBalances;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<LeaveBalances, Long> {
    List<LeaveBalances> findByUserId(Long userId);
}
