package com.dornor.leave_system.entity;

import jakarta.persistence.*;
import lombok.*;

public class Leave_types {
    @Entity
    @Table(name = "leave_types")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class LeaveType {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name",nullable = false, unique = true)
        private String name;

        @Column(name = "description",nullable = false)
        private String description;

        @Column(name = "max_days",nullable = false)
        private Integer maxDays;
    }

}
