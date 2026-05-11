import { Component } from '@angular/core';

import { Application } from '../../../core/model/application.model';
import { ApplicationService } from '../../../core/services/application.service';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent {
  loading = false;
  error = '';

  applications: Application[] = [];

  constructor(private applicationService: ApplicationService) { }

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;
    this.error = '';

    this.applicationService.getApplications().subscribe({
      next: (applications) => {
        this.applications = applications;
      },
      error: (error) => {
        this.error =
          error?.error?.message ||
          'Failed to load dashboard.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  countByStatus(status: string): number {
    return this.applications.filter(app => app.status === status).length;
  }

  get total(): number {
    return this.applications.length;
  }

}
