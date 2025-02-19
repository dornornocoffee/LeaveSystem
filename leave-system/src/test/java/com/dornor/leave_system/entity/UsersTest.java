package com.dornor.leave_system.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UsersTest {

    @Test
    void testUserBuilder() {
        Users user = Users.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("EMPLOYEE")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getRole()).isEqualTo("EMPLOYEE");
    }

    @Test
    void testEqualsAndHashCode() {
        Users user1 = Users.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();

        Users user2 = Users.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();

        Users differentUser = Users.builder()
                .id(2L)
                .username("otheruser")
                .email("other@example.com")
                .build();

        assertThat(user1).isEqualTo(user2);
        assertThat(user1).isNotEqualTo(differentUser);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
        assertThat(user1.hashCode()).isNotEqualTo(differentUser.hashCode());
    }
}
