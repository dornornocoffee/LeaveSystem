import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import * as XLSX from 'xlsx'

@Injectable({
  providedIn: 'root'
})
export class LeaveServiceService {

  http = inject(HttpClient)
  fileName= 'TotalLeave.xlsx'
  selectedMonth = ''
  selectedDepartment= ''

  getLeftDay(): Observable<number> {
    const url = `http://localhost:8080/api/user/2`;
    
    return this.http.get<{ leftCount: number }>(url).pipe(
      map(response => response.leftCount)
    );
  }

  getLeaves(): Observable<any[]> {
    const url = `http://localhost:8080/api/leave-requests`;
    return this.http.get<any[]>(url).pipe(
      map((response) =>
        response.map((leave) => ({
          date: this.formatDateRange(leave.startDate, leave.endDate),
          type: leave.leaveType.name,
          days: this.calculateDays(leave.startDate, leave.endDate),
          status: this.getStatusText(leave.status)
        }))
      )
    );
  }

  private formatDateRange(startDate: string, endDate: string): string {
    return `${this.formatDate(startDate)} - ${this.formatDate(endDate)}`;
  }

  private formatDate(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleDateString('th-TH', { day: '2-digit', month: 'short', year: 'numeric' });
  }

  calculateDays(startDate: string, endDate: string): number {
    const start = new Date(startDate);
    const end = new Date(endDate);
    return Math.ceil((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)) + 1;
  }

  private getStatusText(status: string): string {
    const statusMap: { [key: string]: string } = {
      PENDING: 'รออนุมัติ',
      APPROVED: 'อนุมัติแล้ว',
      REJECTED: 'ถูกปฏิเสธ'
    };
    return statusMap[status] || status;
  }

  getHistoryDetail(): Observable<any[]> {
    const url = `http://localhost:8080/api/leave-requests`;
    return this.http.get<any[]>(url).pipe(
      map((leaves) => {
        const leaveSummary = new Map<string, any>();
  
        leaves.forEach((leave) => {
          if (leave.status === 'APPROVED') {
            const username = leave.user.username;
            const department = leave.user.department;
            const days = this.calculateDays(leave.startDate, leave.endDate);
  
            if (!leaveSummary.has(username)) {
              leaveSummary.set(username, {
                name: username,
                department: department,
                startDate: leave.startDate,
                sickLeave: 0,
                vacationLeave: 0,
                personalLeave: 0,
                total: 0
              });
            }
  
            const userLeave = leaveSummary.get(username);

            switch (leave.leaveType.name) {
              case 'ลาป่วย':
                userLeave.sickLeave += days;
                break;
              case 'ลาพักร้อน':
                userLeave.vacationLeave += days;
                break;
              case 'ลากิจ':
                userLeave.personalLeave += days;
                break;
            }
  
            userLeave.total += days;
          }
        });
  
        return Array.from(leaveSummary.values());
      })
    );
  }

  getPendingReq(): Observable<any[]> {
    const url = `http://localhost:8080/api/leave-requests`
    return this.http.get<any[]>(url).pipe(
      map((requests) => {
        return requests.filter(request => request.status === 'PENDING');
      })
    );

  }

  requestLeave(leaveData: any): void {
    const url = `http://localhost:8080/api/leave-requests`
    this.http.post(url, leaveData).subscribe(
      response => {
        console.log('Leave request submitted successfully');
        window.alert("ส่งคำขอการลางานแล้วเรียบร้อย");
      },
      error => {
        console.error('Error submitting leave request');
        window.alert("เกิดข้อผิดพลาดในการส่งคำขอลา");
      }
    );
  }

  approveReq(id: number): Observable<any> {
    const url = `http://localhost:8080/api/leave-requests/${id}/status?status=APPROVED`;
    return this.http.put(url, null);
  }
  
  cancelReq(id: number): Observable<any> {
    const url = `http://localhost:8080/api/leave-requests/${id}/status?status=REJECTED`;
    return this.http.put(url, null);
  }

  exportexcel(): void
  {
    let element = document.getElementById('excel-table');
    const ws: XLSX.WorkSheet =XLSX.utils.table_to_sheet(element);
 
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
 
    XLSX.writeFile(wb, this.fileName);
 
  }

  departmentSubject = new BehaviorSubject<string>('');
  monthSubject = new BehaviorSubject<string>('');
  selectedDepartment$ = this.departmentSubject.asObservable();
  selectedMonth$ = this.monthSubject.asObservable();

  setDepartment(department: string) {
    this.departmentSubject.next(department);
  }

  setMonth(month: any) {
    this.monthSubject.next(month)
  }
  
}
