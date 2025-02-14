package com.dornor.leave_system.services;

import com.dornor.leave_system.entity.LeaveBalances;
import com.dornor.leave_system.entity.LeaveRequest;
import com.dornor.leave_system.entity.LeaveTypes;
import com.dornor.leave_system.entity.Users;
import com.dornor.leave_system.repository.BalanceRepository;
import com.dornor.leave_system.repository.RequestRepository;
import com.dornor.leave_system.repository.TypeRepository;
import com.dornor.leave_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<LeaveBalances> getRemainingLeaves(int user) {
        return balanceRepository.findByUserId((long) user);
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
        typeRepository.deleteById(Math.toIntExact(id));

    }

    public void saveLeaveBalances(LeaveBalances leaveBalances) {
        balanceRepository.save(leaveBalances);
    }

}
