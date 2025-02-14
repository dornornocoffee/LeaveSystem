import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RequestLeaveComponent } from './components/request-leave/request-leave.component';
import { LeaveHistoryComponent } from './components/leave-history/leave-history.component';
import { HeaderComponent } from './components/header/header.component';
import { AdminComponent } from './components/admin/admin.component';
import { GraphComponent } from './components/graph/graph.component';
import { LeaveDetailsComponent } from './components/leave-details/leave-details.component';
import { MatTableModule } from '@angular/material/table';
import { LatestLeavesComponent } from './components/latest-leaves/latest-leaves.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    RequestLeaveComponent,
    LeaveHistoryComponent,
    HeaderComponent,
    AdminComponent,
    GraphComponent,
    LeaveDetailsComponent,
    LatestLeavesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatTabsModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
  
}
