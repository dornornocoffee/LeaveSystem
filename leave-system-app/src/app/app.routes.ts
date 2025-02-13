import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LeaveFormComponent } from './components/leave-form/leave-form.component';
import { LeaveHistoryComponent } from './components/leave-history/leave-history.component';


export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'request-leave', component: LeaveFormComponent },
  { path: 'leave-history', component: LeaveHistoryComponent },
];

export const appRouting = provideRouter(routes);
