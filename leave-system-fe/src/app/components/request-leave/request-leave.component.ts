import { Component, inject } from '@angular/core';
import { LeaveServiceService } from '../../services/leave-service.service';

@Component({
  selector: 'app-request-leave',
  templateUrl: './request-leave.component.html',
  styleUrl: './request-leave.component.scss'
})
export class RequestLeaveComponent {
  leaveService = inject(LeaveServiceService)

  leaveRequest = {
    user:{
      id:2
    },
    leaveType: {
      id:0
    },
    startDate: '',
    endDate: '',
    status: "PENDING",
    reason: ''
  };

  submitForm(): void {
    this.leaveService.requestLeave(this.leaveRequest)
  }

  cancelForm(): void {
    this.leaveRequest = { 
      user:{
        id:0
      },
      leaveType: {
        id:0
      },
      startDate: '', 
      endDate: '', 
      status: "PENDING",
      reason: '' 
    }
  }


}
