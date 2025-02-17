import { AfterViewInit, Component, OnInit } from '@angular/core';
import { LeaveServiceService } from '../../services/leave-service.service';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-leave-details',
  templateUrl: './leave-details.component.html',
  styleUrl: './leave-details.component.scss'
})
export class LeaveDetailsComponent implements OnInit, AfterViewInit  {
   
  displayedColumns: string[] = ['name', 'department', 'sickLeave', 'vacationLeave', 'personalLeave', 'total'];
  dataSource:any[] = [];
  filteredLeaves: any[] = [];
  leaveChart: any;

  constructor(private leaveService: LeaveServiceService) {}

  ngOnInit(): void {
    this.loadInfo()

    this.leaveService.selectedDepartment$.subscribe(department => {
      this.applyFilters(department, this.leaveService.monthSubject.value);
    });

    this.leaveService.selectedMonth$.subscribe(month => {
      this.applyFilters(this.leaveService.departmentSubject.value, month);
    });

   }

   ngAfterViewInit() {
    this.createChart();
   }

  loadInfo(): void{
    this.leaveService.getHistoryDetail().subscribe(
      (leaves) => {
        this.dataSource = leaves;
        this.applyFilters(
          this.leaveService.departmentSubject.value, 
          this.leaveService.monthSubject.value
        );
      },
      (error) => {
        console.error('Error fetching leaves:', error);
      }
    );



  }

  applyFilters(selectedDepartment: string, selectedMonth: string): void {
    this.filteredLeaves = this.dataSource.filter(leave => {
      const leaveMonth = leave.startDate.substring(0, 7);
      const matchesMonth = selectedMonth ? leaveMonth === selectedMonth : true;
      const matchesDepartment = selectedDepartment ? leave.department === selectedDepartment : true;
      return matchesMonth && matchesDepartment;
    });

    this.updateChart();
  }

  createChart(): void {
    const ctx = document.getElementById("leaveChart") as HTMLCanvasElement;
    this.leaveChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['ลาป่วย', 'ลาพักร้อน', 'ลากิจ'],
        datasets: [{
          label: 'จำนวนวัน',
          data: [0, 0, 0],
          backgroundColor: ['blue']
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

  updateChart(): void {
    if (!this.leaveChart) return;
    const sickLeave = this.filteredLeaves.reduce((total, user) => total + user.sickLeave, 0);
    const vacationLeave = this.filteredLeaves.reduce((total, user) => total + user.vacationLeave, 0);
    const personalLeave = this.filteredLeaves.reduce((total, user) => total + user.personalLeave, 0);

    this.leaveChart.data.datasets[0].data = [sickLeave, vacationLeave, personalLeave];
    this.leaveChart.update();
  }
}
