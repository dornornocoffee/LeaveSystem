package com.dornor.leave_system.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class LeaveBalancesTest {

    @Test
    void testLeaveBalancesBuilder() {
        Users user = Users.builder().id(1L).username("testuser").build();
        LeaveTypes leaveType = LeaveTypes.builder().id(1L).name("Annual Leave").build();

        LeaveBalances leaveBalance = LeaveBalances.builder()
                .id(1L)
                .user(user)
                .leaveType(leaveType)
                .remainingDays(20)
                .year(2025)
                .build();

        assertThat(leaveBalance.getId()).isEqualTo(1L);
        assertThat(leaveBalance.getUser()).isEqualTo(user);
        assertThat(leaveBalance.getLeaveType()).isEqualTo(leaveType);
        assertThat(leaveBalance.getRemainingDays()).isEqualTo(20);
        assertThat(leaveBalance.getYear()).isEqualTo(2025);
    }

    @Test
    void testEqualsAndHashCode() {
        Users user = Users.builder().id(1L).username("testuser").build();
        LeaveTypes leaveType = LeaveTypes.builder().id(1L).name("Annual Leave").build();

        LeaveBalances balance1 = LeaveBalances.builder()
                .id(1L)
                .user(user)
                .leaveType(leaveType)
                .remainingDays(20)
                .year(2025)
                .build();

        LeaveBalances balance2 = LeaveBalances.builder()
                .id(1L)
                .user(user)
                .leaveType(leaveType)
                .remainingDays(20)
                .year(2025)
                .build();

        LeaveBalances differentBalance = LeaveBalances.builder()
                .id(2L)
                .user(user)
                .leaveType(leaveType)
                .remainingDays(15)
                .year(2025)
                .build();

        assertThat(balance1).isEqualTo(balance2);
        assertThat(balance1).isNotEqualTo(differentBalance);
        assertThat(balance1.hashCode()).isEqualTo(balance2.hashCode());
        assertThat(balance1.hashCode()).isNotEqualTo(differentBalance.hashCode());
    }
}
