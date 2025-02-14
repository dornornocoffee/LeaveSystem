import { Component, inject, OnInit } from '@angular/core';
import { LeaveServiceService } from '../../services/leave-service.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent implements OnInit {
  pendingLeaves: any[] = [];

  constructor(private leaveService: LeaveServiceService) {}

  ngOnInit(): void {
    this.leaveService.getPendingReq().subscribe(
      (data) => {
        this.pendingLeaves = data;
        console.log(data);
      },
      (error) => {
        console.error('Error fetching leave requests:', error);
      }
    );
  }

  calculateDays(startDate: string, endDate: string): number {
    return this.leaveService.calculateDays(startDate, endDate);
  }

  approveLeave(id: number): void {
    this.leaveService.approveReq(id).subscribe(
      (response) => {
        console.log('Leave approved successfully', response);
        alert('อนุมัติการลาเรียบร้อยแล้ว');
        this.loadPendingRequests(); 
      },
      (error) => {
        console.error('Error approving leave', error);
      }
    );
  }

  cancelLeave(id: number): void {
    this.leaveService.cancelReq(id).subscribe(
      (response) => {
        console.log('Leave approved successfully', response);
        alert('ปฎิเสธการลาเรียบร้อยแล้ว');
        this.loadPendingRequests(); 
      },
      (error) => {
        console.error('Error approving leave', error);
      }
    );
  }

  loadPendingRequests(): void {
    this.leaveService.getPendingReq().subscribe((data) => {
      this.pendingLeaves = data;
    });
  }
}
