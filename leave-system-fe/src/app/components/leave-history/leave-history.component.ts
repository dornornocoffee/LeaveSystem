import { Component, inject } from '@angular/core';
import { LeaveServiceService } from '../../services/leave-service.service';

@Component({
  selector: 'app-leave-history',
  templateUrl: './leave-history.component.html',
  styleUrl: './leave-history.component.scss'
})
export class LeaveHistoryComponent {

  leaveService = inject(LeaveServiceService)
  selectedMonth=''
  selectedDepartment=''

  exportExcel(): void {
    this.leaveService.exportexcel();
  }

  setMonth() {
    this.leaveService.setMonth(this.selectedMonth)
  }

  setDepartment(){
    this.leaveService.setDepartment(this.selectedDepartment)
  }
  


}
