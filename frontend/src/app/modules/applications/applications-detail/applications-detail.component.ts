import { Component } from '@angular/core';

import { ApplicationService } from '../../../core/services/application.service';
import { DocumentService } from '../../../core/services/document.service';
import { AuthService } from '../../../core/services/auth.service';
import { AuditService } from '../../../core/services/audit.service';

import { Application } from '../../../core/model/application.model';
import { Audit } from '../../../core/model/audit.model';
import { DocumentFile } from '../../../core/model/document.model';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-applications-detail',
  templateUrl: './applications-detail.component.html',
  styleUrls: ['./applications-detail.component.scss']
})
export class ApplicationsDetailComponent {
loading = false;
  actionLoading = false;
  error = '';

  application?: Application;
  documents: DocumentFile[] = [];
  auditEntries: Audit[] = [];

  comment = '';

  constructor(
    public auth: AuthService,
    private route: ActivatedRoute,
    private applicationService: ApplicationService,
    private auditService: AuditService,
    private documentService: DocumentService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  get id(): string {
    return this.route.snapshot.paramMap.get('id')!;
  }

  loadData(): void {
    this.loading = true;
    this.error = '';

    this.applicationService.getApplication(this.id).subscribe({
      next: (application) => {
        this.application = application;
        this.loadDocuments();
        this.loadAudit();
      },
      error: (error) => {
        this.error =
          error?.error?.message ||
          'Failed to load application.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  loadDocuments(): void {
    this.documentService.getDocuments(this.id).subscribe({
      next: (documents) => {
        this.documents = documents;
      }
    });
  }

  loadAudit(): void {
    this.auditService.getAuditTrail(this.id).subscribe({
      next: (audit) => {
        this.auditEntries = audit;
      }
    });
  }

  startReview(): void {
    this.runAction(() => this.applicationService.startReview(this.id));
  }

  completeReview(): void {
    this.runAction(() =>
      this.applicationService.completeReview(this.id, this.comment)
    );
  }

  requestInfo(): void {
    this.runAction(() =>
      this.applicationService.requestInfo(this.id, this.comment)
    );
  }

  approve(): void {
    this.runAction(() =>
      this.applicationService.approve(this.id, this.comment)
    );
  }

  reject(): void {
    this.runAction(() =>
      this.applicationService.reject(this.id, this.comment)
    );
  }

  private runAction(action: any): void {
    this.actionLoading = true;
    this.error = '';

    action().subscribe({
      next: (application: Application) => {
        this.application = application;
        this.comment = '';
        this.loadAudit();
        this.loadDocuments();
      },
      error: (error: any) => {
        this.error =
          error?.error?.message ||
          'Action failed.';
        this.actionLoading = false;
      },
      complete: () => {
        this.actionLoading = false;
      }
    });
  }

  canReview(): boolean {
    return this.auth.hasPermission('REVIEW_APPLICATION');
  }

  canRequestInfo(): boolean {
    return this.auth.hasPermission('REQUEST_INFORMATION');
  }

  canApprove(): boolean {
    return this.auth.hasPermission('APPROVE_APPLICATION');
  }

  canReject(): boolean {
    return this.auth.hasPermission('REJECT_APPLICATION');
  }

}
