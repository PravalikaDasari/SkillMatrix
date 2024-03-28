import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  title: string = '';

  constructor(private router: Router, private titleService: Title) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.setTitleFromRoute();
      }
    });
  }
  setTitleFromRoute() {
    const currentUrl = this.router.url;

    const segments = currentUrl.split('/');
    const lastSegment = segments[segments.length - 1];

    switch (lastSegment) {
      case 'timesheethome':
        this.title = 'Timesheet Home';
        break;
      case 'timesheethistory':
        this.title = 'Timesheet History';
        break;
      case 'timesheetapproval':
        this.title = 'Timesheet Approval';
        break;
      case 'showEmpSkills':
        this.title = 'Update Skills';
        break;
      case 'employeeGap':
        this.title = 'Skill Gap';
        break;
      case 'training':
        this.title = 'Training Recommendations';
        break;
      case 'addSkills':
        this.title = 'Add Skills';
        break;
      case 'empskillgap':
        this.title = 'Employees Skill Gap';
        break;
      case 'trainingsrecommended':
        this.title = 'Employees Recommended For Training';
        break;

      default:
        this.title = 'Dashboard';
        break;
    }

    this.titleService.setTitle(this.title);
  }

}
