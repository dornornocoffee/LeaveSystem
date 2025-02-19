package com.dornor.leave_system.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LeaveTypesTest {

    @Test
    void testLeaveTypesBuilder() {
        LeaveTypes leaveType = LeaveTypes.builder()
                .id(1L)
                .name("Annual Leave")
                .maxDays(20)
                .description("Standard annual leave allocation")
                .build();

        assertThat(leaveType.getId()).isEqualTo(1L);
        assertThat(leaveType.getName()).isEqualTo("Annual Leave");
        assertThat(leaveType.getMaxDays()).isEqualTo(20);
        assertThat(leaveType.getDescription()).isEqualTo("Standard annual leave allocation");
    }

    @Test
    void testEqualsAndHashCode() {
        LeaveTypes type1 = LeaveTypes.builder()
                .id(1L)
                .name("Annual Leave")
                .maxDays(20)
                .description("Standard annual leave")
                .build();

        LeaveTypes type2 = LeaveTypes.builder()
                .id(1L)
                .name("Annual Leave")
                .maxDays(20)
                .description("Standard annual leave")
                .build();

        LeaveTypes differentType = LeaveTypes.builder()
                .id(2L)
                .name("Sick Leave")
                .maxDays(10)
                .description("Medical leave")
                .build();

        assertThat(type1).isEqualTo(type2);
        assertThat(type1).isNotEqualTo(differentType);
        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
        assertThat(type1.hashCode()).isNotEqualTo(differentType.hashCode());
    }
}
