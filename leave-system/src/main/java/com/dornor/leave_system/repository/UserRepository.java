package com.dornor.leave_system.repository;

import com.dornor.leave_system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
