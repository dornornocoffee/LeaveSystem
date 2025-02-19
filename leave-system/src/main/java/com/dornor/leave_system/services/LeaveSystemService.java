package com.dornor.leave_system.services;

import com.dornor.leave_system.entity.*;
import com.dornor.leave_system.repository.BalanceRepository;
import com.dornor.leave_system.repository.RequestRepository;
import com.dornor.leave_system.repository.TypeRepository;
import com.dornor.leave_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveSystemService {
    private UserRepository userRepository;
    private TypeRepository typeRepository;
    private RequestRepository requestRepository;
    private BalanceRepository balanceRepository;

    public LeaveSystemService(UserRepository userRepository, TypeRepository typeRepository, RequestRepository requestRepository, BalanceRepository balanceRepository) {
        this.userRepository = userRepository;
        this.typeRepository = typeRepository;
        this.requestRepository = requestRepository;
        this.balanceRepository = balanceRepository;
    }

    public List<LeaveRequest> getLeaveHistory() {
        return requestRepository.findAll();
    }

    public List<LeaveBalances> getRemainingLeaves(Long id) {
        return balanceRepository.findByUserId(id);
    }

    public void saveLeave(LeaveRequest leaveRequest) {
        requestRepository.save(leaveRequest);
    }

    public void saveLeaveType(LeaveTypes leaveTypes) {
        typeRepository.save(leaveTypes);
    }

    public void saveUsers(Users users) {
        userRepository.save(users);
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public List<LeaveTypes> getLeaveTypes() {
        return typeRepository.findAll();
    }

    public void deleteLeaveType(Long id) {
        typeRepository.deleteById(id);
    }

    public void saveLeaveBalances(LeaveBalances leaveBalances) {
        balanceRepository.save(leaveBalances);
    }

    public Optional<Users> getUsersById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void approveLeaveRequest(Long leaveRequestId, String status) {
        LeaveRequest leaveRequest = requestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (status == null || (!status.equals("APPROVED") && !status.equals("REJECTED"))) {
            throw new IllegalArgumentException("Invalid status value");
        }

        leaveRequest.setStatus(LeaveStatus.valueOf(status));
        requestRepository.save(leaveRequest);
    }

}
