package com.dornor.leave_system.controller;

import com.dornor.leave_system.entity.*;
import com.dornor.leave_system.services.LeaveSystemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class LeaveSystemController {
    private final LeaveSystemService leaveSystemService;

    public LeaveSystemController(LeaveSystemService leaveSystemService) {
        this.leaveSystemService = leaveSystemService;
    }

    @GetMapping("/leave-requests")
    public List<LeaveRequest> leaveSystem() {
        return leaveSystemService.getLeaveHistory();
    }

    @PostMapping("/leave-requests")
    public void createRequests(@RequestBody LeaveRequest leave_request) {
        leaveSystemService.saveLeave(leave_request);
    }

    //for admin
    @PutMapping("/leave-requests/{id}/status")
    public void updateRequests(@PathVariable Long id,@RequestParam("status") String status) {
        leaveSystemService.approveLeaveRequest(id, status);
    }

    @GetMapping("/leave-balances/{id}")
    public List<LeaveBalances> leaveBalances(@PathVariable int id) {
        return leaveSystemService.getRemainingLeaves(id);
    }

    @PostMapping("/user")
    public void createUser(@RequestBody Users user) {
        leaveSystemService.saveUsers(user);

    }

    @PostMapping("/leave-types")
    public void createLeaveTypes(@RequestBody LeaveTypes leave_types) {
        leaveSystemService.saveLeaveType(leave_types);
    }

    @GetMapping("/user")
    public List<Users> getUsers() {
        return leaveSystemService.getUsers();
    }

    @GetMapping("/leave-types")
    public List<LeaveTypes> getLeaveTypes() {
        return leaveSystemService.getLeaveTypes();
    }

    @DeleteMapping("/leave-types/{id}")
    public void deleteLeaveTypes(@PathVariable Long id) {

        leaveSystemService.deleteLeaveType(id);
    }

    @PostMapping("/leave-balances")
    public void createLeaveBalances(@RequestBody LeaveBalances leave_balances) {
        leaveSystemService.saveLeaveBalances(leave_balances);
    }

    @GetMapping("/user/{id}")
    public Optional<Users> getUser(@PathVariable Long id) {
        return leaveSystemService.getUsersById(id);
    }
}
