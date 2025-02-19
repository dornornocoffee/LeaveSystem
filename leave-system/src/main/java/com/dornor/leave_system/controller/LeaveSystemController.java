package com.dornor.leave_system.controller;

import com.dornor.leave_system.entity.*;
import com.dornor.leave_system.services.LeaveSystemService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<LeaveRequest>> leaveSystem() {
        try {
            return ResponseEntity.ok(leaveSystemService.getLeaveHistory());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/leave-requests")
    public ResponseEntity<Void> createRequests(@RequestBody LeaveRequest leave_request) {
        try {
            leaveSystemService.saveLeave(leave_request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //for admin
    @PutMapping("/leave-requests/{id}/status")
    public ResponseEntity<LeaveRequest> updateLeaveRequestStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            leaveSystemService.approveLeaveRequest(id, status);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build(); // Changed from throw e
        }
    }

    @GetMapping("/leave-balances/{id}")
    public List<LeaveBalances> leaveBalances(@PathVariable Long id) {
        return leaveSystemService.getRemainingLeaves(id);
    }

    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@RequestBody Users user) {
        try {
            leaveSystemService.saveUsers(user);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/leave-types")
    public ResponseEntity<Void> createLeaveTypes(@RequestBody LeaveTypes leave_types) {
        try {
            leaveSystemService.saveLeaveType(leave_types);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
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
    public ResponseEntity<Void> deleteLeaveTypes(@PathVariable Long id) {
        try {
            leaveSystemService.deleteLeaveType(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/leave-balances")
    public ResponseEntity<Void> createLeaveBalances(@RequestBody LeaveBalances leave_balances) {
        try {
            leaveSystemService.saveLeaveBalances(leave_balances);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<Users>> getUser(@PathVariable Long id) {
        Optional<Users> user = leaveSystemService.getUsersById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
