package com.dornor.leave_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave_request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String user;

    @Column(name = "leave_type_id", nullable = false)
    private String leaveType;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Column(name = "status",nullable = false)
    private String status; // Example: "PENDING", "APPROVED", "REJECTED"

    @Column(name = "reason",columnDefinition = "TEXT")
    private String reason;
}