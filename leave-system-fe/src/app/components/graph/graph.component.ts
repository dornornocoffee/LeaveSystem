import { Component, AfterViewInit  } from '@angular/core';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrl: './graph.component.scss'
})
export class GraphComponent implements AfterViewInit {
  
  ngAfterViewInit() {
    new Chart("leaveChart", {
      type: 'bar',
      data: {
        labels: ['ลาป่วย', 'ลาพักร้อน', 'ลากิจ'],
        datasets: [{
          label: 'จำนวนวัน',
          data: [45, 30, 15],  // Sample data
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
  }

}
