import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  selectedIndex = 0;
  title: any;

  constructor(private router: Router) {
    // Sync tab index with URL
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateSelectedTab(event.url);
      }
    });
  }

  onTabChange(index: number) {
    // Navigate based on tab index
    const routes = ['dashboard', 'request-leave', 'leave-history'];
    this.router.navigate([routes[index]]);
  }

  updateSelectedTab(url: string) {
    if (url.includes('request-leave')) this.selectedIndex = 1;
    else if (url.includes('leave-history')) this.selectedIndex = 2;
    else this.selectedIndex = 0; // Default to dashboard
  }
}
