package com.dornor.leave_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_balances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave_balances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @Column(name = "leave_type_id", nullable = false)
    private String leaveType_id;

    @Column(name = "year",nullable = false)
    private Integer year;

    @Column(name = "remaining_days",nullable = false)
    private Integer remainingDays;
}