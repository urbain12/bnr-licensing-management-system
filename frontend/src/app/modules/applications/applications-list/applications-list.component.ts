import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { ApplicationService } from '../../../core/services/application.service';
import { Application } from '../../../core/model/application.model';

type ViewMode = 'ALL' | 'REVIEW' | 'APPROVAL';

@Component({
  selector: 'app-applications-list',
  templateUrl: './applications-list.component.html',
  styleUrls: ['./applications-list.component.scss']
})
export class ApplicationsListComponent implements OnInit {loading = false;
  error = '';

  viewMode: ViewMode = 'ALL';

  displayedColumns = [
    'referenceNumber',
    'name',
    'licenseType',
    'status',
    'actions'
  ];

  applications: Application[] = [];

  constructor(
    public auth: AuthService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit(): void {
    const role = this.auth.getRole();

    if (role === 'REVIEWER') {
      this.viewMode = 'REVIEW';
    }

    if (role === 'APPROVER') {
      this.viewMode = 'APPROVAL';
    }

    this.loadApplications();
  }

  loadApplications(): void {
    this.loading = true;
    this.error = '';

    this.applicationService.getApplications().subscribe({
      next: (applications) => {
        this.applications = applications;
      },
      error: (error) => {
        this.error =
          error?.error?.message ||
          'Failed to load applications.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  setView(mode: ViewMode): void {
    this.viewMode = mode;
  }

  get filteredApplications(): Application[] {
    if (this.viewMode === 'REVIEW') {
      return this.applications.filter(app =>
        app.status === 'SUBMITTED' ||
        app.status === 'UNDER_REVIEW' ||
        app.status === 'INFO_REQUESTED' ||
        app.status === 'RESUBMITTED'
      );
    }

    if (this.viewMode === 'APPROVAL') {
      return this.applications.filter(app =>
        app.status === 'REVIEW_COMPLETED'
      );
    }

    return this.applications;
  }

}
