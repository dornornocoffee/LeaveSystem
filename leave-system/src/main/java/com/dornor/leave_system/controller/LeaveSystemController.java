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
    public List<LeaveRequest> leaveSystem() {
        return leaveSystemService.getLeaveHistory();
    }

    @PostMapping("/leave-requests")
    public void createRequests(@RequestBody LeaveRequest leave_request) {
        leaveSystemService.saveLeave(leave_request);
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
            throw e;
        }
    }

    @GetMapping("/leave-balances/{id}")
    public List<LeaveBalances> leaveBalances(@PathVariable Long id) {
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
    public ResponseEntity<Optional<Users>> getUser(@PathVariable Long id) {
        Optional<Users> user = leaveSystemService.getUsersById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
