import { Component, OnInit } from '@angular/core';
import { LeaveServiceService } from '../../services/leave-service.service';

@Component({
  selector: 'app-leave-details',
  templateUrl: './leave-details.component.html',
  styleUrl: './leave-details.component.scss'
})
export class LeaveDetailsComponent implements OnInit {
   
  displayedColumns: string[] = ['name', 'department', 'sickLeave', 'vacationLeave', 'personalLeave', 'total'];
  dataSource:any[] = [];
  filteredLeaves: any[] = [];

  selectedMonth: string = '';
  selectedDepartment: string = '';

  constructor(private leaveService: LeaveServiceService) {}

  ngOnInit(): void {
    this.loadInfo()
   }

  

  loadInfo(): void{
    this.leaveService.getHistoryDetail().subscribe(
      (leaves) => {
        this.dataSource = leaves;
      },
      (error) => {
        console.error('Error fetching leaves:', error);
      }
    );
  }

}
