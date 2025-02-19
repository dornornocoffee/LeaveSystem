package com.dornor.leave_system.controller;

import com.dornor.leave_system.config.TestSecurityConfig;
import com.dornor.leave_system.entity.*;
import com.dornor.leave_system.services.LeaveSystemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LeaveSystemController.class)
@Import(TestSecurityConfig.class)
public class LeaveSystemControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private LeaveSystemService leaveSystemService;

        @Autowired
        private ObjectMapper objectMapper;

        private Users testUser;
        private LeaveRequest testLeaveRequest;
        private LeaveTypes testLeaveType;
        private LeaveBalances testLeaveBalance;

        @BeforeEach
        void setUp() {
                testUser = Users.builder()
                                .id(1L)
                                .username("testuser")
                                .email("test@example.com")
                                .role("EMPLOYEE")
                                .department("IT")
                                .leftCount(10)
                                .build();

                testLeaveType = LeaveTypes.builder()
                                .id(1L)
                                .name("Annual Leave")
                                .description("Regular annual leave")
                                .maxDays(14)
                                .build();

                testLeaveRequest = LeaveRequest.builder()
                                .id(1L)
                                .user(testUser)
                                .leaveType(testLeaveType)
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(2))
                                .status(LeaveStatus.PENDING)
                                .reason("Vacation")
                                .build();

                testLeaveBalance = LeaveBalances.builder()
                                .id(1L)
                                .user(testUser)
                                .leaveType(testLeaveType)
                                .year(2025)
                                .remainingDays(10)
                                .build();
        }

        @Test
        void getLeaveRequests_ShouldReturnListOfLeaveRequests() throws Exception {
                List<LeaveRequest> leaveRequests = Arrays.asList(testLeaveRequest);
                when(leaveSystemService.getLeaveHistory()).thenReturn(leaveRequests);

                mockMvc.perform(get("/api/leave-requests"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value(testLeaveRequest.getId()))
                                .andExpect(jsonPath("$[0].status").value(testLeaveRequest.getStatus().toString()));
        }

        @Test
        void createLeaveRequest_ShouldCreateNewLeaveRequest() throws Exception {
                mockMvc.perform(post("/api/leave-requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLeaveRequest)))
                                .andExpect(status().isOk());

                verify(leaveSystemService).saveLeave(any(LeaveRequest.class));
        }

        @Test
        void updateLeaveRequestStatus_ShouldUpdateStatus() throws Exception {
                mockMvc.perform(put("/api/leave-requests/{id}/status", testLeaveRequest.getId())
                                .param("status", "APPROVED"))
                                .andExpect(status().isOk());

                verify(leaveSystemService).approveLeaveRequest(eq(testLeaveRequest.getId()), eq("APPROVED"));
        }

        @Test
        void updateLeaveRequestStatus_WithInvalidStatus_ShouldReturnBadRequest() throws Exception {
                doThrow(new IllegalArgumentException("Invalid status value"))
                                .when(leaveSystemService).approveLeaveRequest(1L, "INVALID_STATUS");

                mockMvc.perform(put("/api/leave-requests/{id}/status", 1L)
                                .param("status", "INVALID_STATUS")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void updateLeaveRequestStatus_WithNonExistentId_ShouldReturnNotFound() throws Exception {
                doThrow(new RuntimeException("Leave request not found"))
                                .when(leaveSystemService).approveLeaveRequest(999L, "APPROVED");

                mockMvc.perform(put("/api/leave-requests/{id}/status", 999L)
                                .param("status", "APPROVED")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        void getLeaveTypes_ShouldReturnListOfLeaveTypes() throws Exception {
                List<LeaveTypes> leaveTypes = Arrays.asList(testLeaveType);
                when(leaveSystemService.getLeaveTypes()).thenReturn(leaveTypes);

                mockMvc.perform(get("/api/leave-types"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value(testLeaveType.getId()))
                                .andExpect(jsonPath("$[0].name").value(testLeaveType.getName()));
        }

        @Test
        void createLeaveType_ShouldCreateNewLeaveType() throws Exception {
                mockMvc.perform(post("/api/leave-types")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLeaveType)))
                                .andExpect(status().isOk());

                verify(leaveSystemService).saveLeaveType(any(LeaveTypes.class));
        }

        @Test
        void deleteLeaveType_ShouldDeleteLeaveType() throws Exception {
                mockMvc.perform(delete("/api/leave-types/{id}", 1L))
                                .andExpect(status().isOk());

                verify(leaveSystemService).deleteLeaveType(1L);
        }

        @Test
        void getLeaveBalances_ShouldReturnListOfLeaveBalances() throws Exception {
                List<LeaveBalances> leaveBalances = Arrays.asList(testLeaveBalance);
                when(leaveSystemService.getRemainingLeaves(1L)).thenReturn(leaveBalances);

                mockMvc.perform(get("/api/leave-balances/{id}", 1L))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value(testLeaveBalance.getId()))
                                .andExpect(jsonPath("$[0].year").value(testLeaveBalance.getYear()));
        }

        @Test
        void createLeaveBalance_ShouldCreateNewLeaveBalance() throws Exception {
                mockMvc.perform(post("/api/leave-balances")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLeaveBalance)))
                                .andExpect(status().isOk());

                verify(leaveSystemService).saveLeaveBalances(any(LeaveBalances.class));
        }

        @Test
        void getAllUsers_ShouldReturnListOfUsers() throws Exception {
                List<Users> users = Arrays.asList(testUser);
                when(leaveSystemService.getUsers()).thenReturn(users);

                mockMvc.perform(get("/api/user"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value(testUser.getId()))
                                .andExpect(jsonPath("$[0].username").value(testUser.getUsername()));
        }

        @Test
        void createUser_ShouldCreateNewUser() throws Exception {
                mockMvc.perform(post("/api/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isOk());

                verify(leaveSystemService).saveUsers(any(Users.class));
        }

        @Test
        void updateLeaveRequestStatus_WithValidData_ShouldUpdateStatus() throws Exception {
                mockMvc.perform(put("/api/leave-requests/{id}/status", 1L)
                                .param("status", "APPROVED")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                verify(leaveSystemService).approveLeaveRequest(1L, "APPROVED");
        }

        @Test
        void getUser_WithExistingId_ShouldReturnUser() throws Exception {
                when(leaveSystemService.getUsersById(1L)).thenReturn(Optional.of(testUser));

                mockMvc.perform(get("/api/user/{id}", 1L))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(testUser.getId()))
                                .andExpect(jsonPath("$.username").value(testUser.getUsername()));
        }

        @Test
        void getUser_WithNonExistentId_ShouldReturnNotFound() throws Exception {
                when(leaveSystemService.getUsersById(99L)).thenReturn(Optional.empty());

                mockMvc.perform(get("/api/user/{id}", 99L))
                                .andExpect(status().isNotFound());
        }

        @Test
        void createLeaveRequest_WithInvalidData_ShouldReturnBadRequest() throws Exception {
                doThrow(new IllegalArgumentException("Invalid data"))
                                .when(leaveSystemService).saveLeave(any(LeaveRequest.class));

                mockMvc.perform(post("/api/leave-requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLeaveRequest)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createLeaveBalance_WithInvalidData_ShouldReturnBadRequest() throws Exception {
                doThrow(new IllegalArgumentException("Invalid data"))
                                .when(leaveSystemService).saveLeaveBalances(any(LeaveBalances.class));

                mockMvc.perform(post("/api/leave-balances")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLeaveBalance)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createLeaveType_WithInvalidData_ShouldReturnBadRequest() throws Exception {
                doThrow(new IllegalArgumentException("Invalid data"))
                                .when(leaveSystemService).saveLeaveType(any(LeaveTypes.class));

                mockMvc.perform(post("/api/leave-types")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLeaveType)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void deleteLeaveType_WithNonExistentId_ShouldReturnNotFound() throws Exception {
                doThrow(new RuntimeException("Leave type not found"))
                                .when(leaveSystemService).deleteLeaveType(999L);

                mockMvc.perform(delete("/api/leave-types/{id}", 999L))
                                .andExpect(status().isNotFound());
        }

        @Test
        void updateLeaveRequestStatus_WithUnexpectedError_ShouldReturnInternalServerError() throws Exception {
                doThrow(new RuntimeException("Unexpected error"))
                                .when(leaveSystemService).approveLeaveRequest(1L, "APPROVED");

                mockMvc.perform(put("/api/leave-requests/{id}/status", 1L)
                                .param("status", "APPROVED"))
                                .andExpect(status().isInternalServerError());
        }

        @Test
        void deleteLeaveType_WithUnexpectedError_ShouldReturnInternalServerError() throws Exception {
                doThrow(new RuntimeException("Unexpected error"))
                                .when(leaveSystemService).deleteLeaveType(1L);

                mockMvc.perform(delete("/api/leave-types/{id}", 1L))
                                .andExpect(status().isInternalServerError());
        }

        @Test
        void createUser_WithInvalidData_ShouldReturnBadRequest() throws Exception {
                doThrow(new IllegalArgumentException("Invalid data"))
                                .when(leaveSystemService).saveUsers(any(Users.class));

                mockMvc.perform(post("/api/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void getLeaveRequests_WithError_ShouldReturnInternalServerError() throws Exception {
                when(leaveSystemService.getLeaveHistory())
                                .thenThrow(new RuntimeException("Database error"));

                mockMvc.perform(get("/api/leave-requests"))
                                .andExpect(status().isInternalServerError());
        }

}
