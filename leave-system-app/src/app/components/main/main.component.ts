import { Component } from '@angular/core';
import { DashboardComponent } from "../dashboard/dashboard.component";
import { LeaveHistoryComponent } from "../leave-history/leave-history.component";


@Component({
  selector: 'app-main',
  standalone: true,
  imports: [DashboardComponent, LeaveHistoryComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

}
