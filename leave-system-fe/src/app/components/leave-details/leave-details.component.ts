import { Component } from '@angular/core';

@Component({
  selector: 'app-leave-details',
  templateUrl: './leave-details.component.html',
  styleUrl: './leave-details.component.scss'
})
export class LeaveDetailsComponent {
   // Define the table columns
   displayedColumns: string[] = ['name', 'department', 'sickLeave', 'vacationLeave', 'personalLeave', 'total'];

   // Define the data source (array of objects)
   dataSource = [
     { name: 'สมชาย ใจดี', department: 'IT', sickLeave: 2, vacationLeave: 1, personalLeave: 0, total: 3 },
     { name: 'สมหญิง รักงาน', department: 'HR', sickLeave: 1, vacationLeave: 2, personalLeave: 1, total: 4 }
   ];
}
