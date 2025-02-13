package com.dornor.leave_system.controller;

import com.dornor.leave_system.entity.LeaveRequest;
import com.dornor.leave_system.entity.LeaveTypes;
import com.dornor.leave_system.entity.Users;
import com.dornor.leave_system.services.LeaveSystemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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
    @PutMapping("/leave-requests/{id}")
    public void updateRequests(@PathVariable int id, @RequestBody LeaveRequest leave_request) {

    }

    @GetMapping("/leave-balances")
    public String leaveBalances() {
        return "leave-balances";
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
}
