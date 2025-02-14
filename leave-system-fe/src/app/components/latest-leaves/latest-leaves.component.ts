import { Component } from '@angular/core';

@Component({
  selector: 'app-latest-leaves',
  templateUrl: './latest-leaves.component.html',
  styleUrl: './latest-leaves.component.scss'
})
export class LatestLeavesComponent {
  displayedColumns: string[] = ['date', 'type', 'days', 'status'];
  dataSource = [
    { date: '10-15 ก.พ. 2567', type: 'ลาพักร้อน', days: 5, status: 'รออนุมัติ' },
    { date: '5 ม.ค. 2567', type: 'ลาป่วย', days: 1, status: 'อนุมัติแล้ว' }
  ];
}
