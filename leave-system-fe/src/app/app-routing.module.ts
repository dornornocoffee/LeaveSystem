import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RequestLeaveComponent } from './components/request-leave/request-leave.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LeaveHistoryComponent } from './components/leave-history/leave-history.component';
import { AdminComponent } from './components/admin/admin.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'request-leave', component: RequestLeaveComponent },
  { path: 'leave-history', component: LeaveHistoryComponent },
  { path: 'submit-form', component: AdminComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
