import { Component, AfterViewInit  } from '@angular/core';
import Chart from 'chart.js/auto';
import { LeaveServiceService } from '../../services/leave-service.service';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrl: './graph.component.scss'
})
export class GraphComponent implements AfterViewInit {

  constructor(private leaveService: LeaveServiceService) {}
  
  ngAfterViewInit() {
    this.leaveService.getHistoryDetail().subscribe((data) => {
      const sickLeave = data.reduce((total, user) => total + user.sickLeave, 0);
      const vacationLeave = data.reduce((total, user) => total + user.vacationLeave, 0);
      const personalLeave = data.reduce((total, user) => total + user.personalLeave, 0);

      // Now initialize the chart with the data
      new Chart("leaveChart", {
        type: 'bar',
        data: {
          labels: ['ลาป่วย', 'ลาพักร้อน', 'ลากิจ'],
          datasets: [{
            label: 'จำนวนวัน',
            data: [sickLeave, vacationLeave, personalLeave], // Use the processed data here
            backgroundColor: 'blue'
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: { display: true }
          }
        }
      });
    });
  }

}
