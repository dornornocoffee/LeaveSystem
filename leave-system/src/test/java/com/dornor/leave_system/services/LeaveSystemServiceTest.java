package com.dornor.leave_system.services;

import com.dornor.leave_system.entity.*;
import com.dornor.leave_system.repository.BalanceRepository;
import com.dornor.leave_system.repository.RequestRepository;
import com.dornor.leave_system.repository.TypeRepository;
import com.dornor.leave_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveSystemServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private LeaveSystemService leaveSystemService;

    private Users testUser;
    private LeaveRequest testLeaveRequest;
    private LeaveTypes testLeaveType;
    private LeaveBalances testLeaveBalance;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = Users.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("EMPLOYEE")
                .department("IT")
                .leftCount(10)
                .build();

        // Setup test leave type
        testLeaveType = LeaveTypes.builder()
                .id(1L)
                .name("Annual Leave")
                .description("Regular annual leave")
                .maxDays(14)
                .build();

        // Setup test leave request
        testLeaveRequest = LeaveRequest.builder()
                .id(1L)
                .user(testUser)
                .leaveType(testLeaveType)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .status(LeaveStatus.PENDING)
                .reason("Vacation")
                .build();

        // Setup test leave balance
        testLeaveBalance = LeaveBalances.builder()
                .id(1L)
                .user(testUser)
                .leaveType(testLeaveType)
                .year(2025)
                .remainingDays(10)
                .build();
    }

    @Test
    void getLeaveHistory_ShouldReturnAllLeaveRequests() {
        List<LeaveRequest> expectedRequests = Arrays.asList(testLeaveRequest);
        when(requestRepository.findAll()).thenReturn(expectedRequests);

        List<LeaveRequest> actualRequests = leaveSystemService.getLeaveHistory();

        assertThat(actualRequests).isEqualTo(expectedRequests);
        verify(requestRepository).findAll();
    }

    @Test
    void saveLeave_ShouldSaveLeaveRequest() {
        when(requestRepository.save(any(LeaveRequest.class))).thenReturn(testLeaveRequest);

        leaveSystemService.saveLeave(testLeaveRequest);

        verify(requestRepository).save(testLeaveRequest);
    }

    @Test
    void approveLeaveRequest_ShouldUpdateLeaveRequestStatus() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(testLeaveRequest));
        when(requestRepository.save(any(LeaveRequest.class))).thenReturn(testLeaveRequest);

        leaveSystemService.approveLeaveRequest(1L, "APPROVED");

        verify(requestRepository).findById(1L);
        verify(requestRepository).save(any(LeaveRequest.class));
    }

    @Test
    void approveLeaveRequest_WithInvalidStatus_ShouldThrowException() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(testLeaveRequest));

        assertThrows(IllegalArgumentException.class, () -> {
            leaveSystemService.approveLeaveRequest(1L, "INVALID_STATUS");
        });
    }

    @Test
    void approveLeaveRequest_WithNullStatus_ShouldThrowException() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(testLeaveRequest));

        assertThrows(IllegalArgumentException.class, () -> {
            leaveSystemService.approveLeaveRequest(1L, null);
        });
    }

    @Test
    void approveLeaveRequest_WithNonExistentId_ShouldThrowException() {
        when(requestRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            leaveSystemService.approveLeaveRequest(999L, "APPROVED");
        });
    }

    @Test
    void getRemainingLeaves_ShouldReturnUserLeaveBalances() {
        List<LeaveBalances> expectedBalances = Arrays.asList(testLeaveBalance);
        when(balanceRepository.findByUserId(1L)).thenReturn(expectedBalances);

        List<LeaveBalances> actualBalances = leaveSystemService.getRemainingLeaves(1L);

        assertThat(actualBalances).isEqualTo(expectedBalances);
        verify(balanceRepository).findByUserId(1L);
    }

    @Test
    void saveLeaveType_ShouldSaveLeaveType() {
        when(typeRepository.save(any(LeaveTypes.class))).thenReturn(testLeaveType);

        leaveSystemService.saveLeaveType(testLeaveType);

        verify(typeRepository).save(testLeaveType);
    }

    @Test
    void saveUsers_ShouldSaveUser() {
        when(userRepository.save(any(Users.class))).thenReturn(testUser);

        leaveSystemService.saveUsers(testUser);

        verify(userRepository).save(testUser);
    }

    @Test
    void getUsers_ShouldReturnAllUsers() {
        List<Users> expectedUsers = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<Users> actualUsers = leaveSystemService.getUsers();

        assertThat(actualUsers).isEqualTo(expectedUsers);
        verify(userRepository).findAll();
    }

    @Test
    void getUsersById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<Users> result = leaveSystemService.getUsersById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testUser);
        verify(userRepository).findById(1L);
    }

    @Test
    void getUsersById_WithNonExistentId_ShouldReturnEmpty() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Users> result = leaveSystemService.getUsersById(999L);

        assertThat(result).isEmpty();
        verify(userRepository).findById(999L);
    }

    @Test
    void getLeaveTypes_ShouldReturnAllLeaveTypes() {
        List<LeaveTypes> expectedTypes = Arrays.asList(testLeaveType);
        when(typeRepository.findAll()).thenReturn(expectedTypes);

        List<LeaveTypes> actualTypes = leaveSystemService.getLeaveTypes();

        assertThat(actualTypes).isEqualTo(expectedTypes);
        verify(typeRepository).findAll();
    }

    @Test
    void deleteLeaveType_ShouldDeleteLeaveType() {
        doNothing().when(typeRepository).deleteById(1L);

        leaveSystemService.deleteLeaveType(1L);

        verify(typeRepository).deleteById(1L);
    }

    @Test
    void saveLeaveBalances_ShouldSaveLeaveBalance() {
        when(balanceRepository.save(any(LeaveBalances.class))).thenReturn(testLeaveBalance);

        leaveSystemService.saveLeaveBalances(testLeaveBalance);

        verify(balanceRepository).save(testLeaveBalance);
    }
}
