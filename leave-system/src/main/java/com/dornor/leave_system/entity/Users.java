package com.dornor.leave_system.entity;

import jakarta.persistence.*;
import lombok.*;

public class Users {
    @Entity
    @Table(name = "users")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "username",nullable = false, unique = true)
        private String username;

        @Column(name = "email",nullable = false, unique = true)
        private String email;

        @Column(name = "role",nullable = false)
        private String role;

        @Column(name = "department",nullable = false)
        private String department;
    }
}
