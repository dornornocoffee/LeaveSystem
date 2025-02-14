import { Component, inject, OnInit, signal } from '@angular/core';
import { LeaveServiceService } from '../../services/leave-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  allLeaves = signal(0)
  pendingDays = signal(0)
  leftDays = signal(0)
  leaveService = inject(LeaveServiceService)
  
  ngOnInit(): void {
    this.leaveService.getLeftDay().subscribe(leftCount => {
      this.leftDays.set(leftCount)
    });
    this.leaveService.getPendingReq().subscribe(pending => {
      this.pendingDays.set(pending.length)
    })
    this.leaveService.getHistoryDetail().subscribe(all => {
      all.forEach((userLeave) => {
        this.allLeaves.set(userLeave.total);
      });
    });
  }
  

}
