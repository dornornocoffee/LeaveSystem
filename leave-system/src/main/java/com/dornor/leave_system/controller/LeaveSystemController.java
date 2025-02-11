package com.dornor.leave_system.controller;

import com.dornor.leave_system.entity.LeaveRequest;
import com.dornor.leave_system.services.LeaveSystemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LeaveSystemController {
    private final LeaveSystemService leaveSystemService;

    public LeaveSystemController(LeaveSystemService leaveSystemService) {
        this.leaveSystemService = leaveSystemService;
    }

    @GetMapping("/leave-requests")
    public String leaveSystem() {
        return "leave-system";
    }

    @PostMapping("/leave-requests")
    public void createRequests(@RequestBody LeaveRequest leave_request) {

    }

    //for admin
    @PutMapping("/leave-requests/{id}")
    public void updateRequests(@PathVariable int id, @RequestBody LeaveRequest leave_request) {

    }

    @GetMapping("/leave-balances")
    public String leaveBalances() {
        return "leave-balances";
    }
}
