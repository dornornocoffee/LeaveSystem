import { Component, OnInit } from '@angular/core';
import { LeaveServiceService } from '../../services/leave-service.service';

@Component({
  selector: 'app-latest-leaves',
  templateUrl: './latest-leaves.component.html',
  styleUrl: './latest-leaves.component.scss'
})
export class LatestLeavesComponent implements OnInit {
  displayedColumns: string[] = ['date', 'type', 'days', 'status'];
  dataSource: any[] = [];

  constructor(private leaveService: LeaveServiceService) {}

  ngOnInit(): void {
    this.leaveService.getLeaves().subscribe(
      (leaves) => {
        this.dataSource = leaves;
      },
      (error) => {
        console.error('Error fetching leaves:', error);
      }
    );
  }
}
