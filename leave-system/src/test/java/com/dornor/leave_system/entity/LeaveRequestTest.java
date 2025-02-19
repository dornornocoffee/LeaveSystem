package com.dornor.leave_system.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class LeaveRequestTest {

    @Test
    void testLeaveRequestBuilder() {
        Users user = Users.builder().id(1L).username("testuser").build();
        LeaveTypes leaveType = LeaveTypes.builder().id(1L).name("Annual Leave").build();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .id(1L)
                .user(user)
                .leaveType(leaveType)
                .startDate(startDate)
                .endDate(endDate)
                .reason("Vacation")
                .status(LeaveStatus.PENDING)
                .build();

        assertThat(leaveRequest.getId()).isEqualTo(1L);
        assertThat(leaveRequest.getUser()).isEqualTo(user);
        assertThat(leaveRequest.getLeaveType()).isEqualTo(leaveType);
        assertThat(leaveRequest.getStartDate()).isEqualTo(startDate);
        assertThat(leaveRequest.getEndDate()).isEqualTo(endDate);
        assertThat(leaveRequest.getReason()).isEqualTo("Vacation");
        assertThat(leaveRequest.getStatus()).isEqualTo(LeaveStatus.PENDING);
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        Users user = Users.builder().id(1L).username("testuser").build();
        LeaveTypes leaveType = LeaveTypes.builder().id(1L).name("Annual Leave").build();

        LeaveRequest request1 = LeaveRequest.builder()
                .id(1L)
                .user(user)
                .leaveType(leaveType)
                .startDate(startDate)
                .endDate(endDate)
                .reason("Test reason")
                .status(LeaveStatus.PENDING)
                .build();

        LeaveRequest request2 = LeaveRequest.builder()
                .id(1L)
                .user(user)
                .leaveType(leaveType)
                .startDate(startDate)
                .endDate(endDate)
                .reason("Test reason")
                .status(LeaveStatus.PENDING)
                .build();

        LeaveRequest differentRequest = LeaveRequest.builder()
                .id(2L)
                .user(user)
                .leaveType(leaveType)
                .startDate(startDate)
                .endDate(endDate)
                .reason("Different reason")
                .status(LeaveStatus.APPROVED)
                .build();

        assertThat(request1).isEqualTo(request2);
        assertThat(request1).isNotEqualTo(differentRequest);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(differentRequest.hashCode());
    }
}
