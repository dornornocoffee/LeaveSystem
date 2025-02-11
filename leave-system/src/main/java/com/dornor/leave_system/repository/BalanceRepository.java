package com.dornor.leave_system.repository;

import com.dornor.leave_system.entity.Leave_balances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Leave_balances, Long> {
}
