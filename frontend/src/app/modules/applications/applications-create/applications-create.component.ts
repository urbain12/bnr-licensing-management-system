import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';

@Component({
  selector: 'app-applications-create',
  templateUrl: './applications-create.component.html',
  styleUrls: ['./applications-create.component.scss']
})
export class ApplicationsCreateComponent {
  
loading = false;
  error = '';

  selectedFiles: any = {};

  form = this.fb.group({
    institutionName: ['', Validators.required],
    licenseType: ['', Validators.required],
    businessAddress: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private applicationService: ApplicationService
  ) {}

  onFilesChanged(files: any): void {
    this.selectedFiles = files;
  }

  submit(): void {
    this.error = '';

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const requiredFiles = [
      'MARKET_RISK_MANAGEMENT_POLICY',
      'CLIENT_ASSET_PROTECTION_POLICY',
      'TRADING_POLICY'
    ];

    const missingFile = requiredFiles.some(code => !this.selectedFiles[code]);

    if (missingFile) {
      this.error = 'Please upload all required documents.';
      return;
    }

    this.loading = true;

    this.applicationService.createApplication({
      institutionName: this.form.value.institutionName!,
      licenseType: this.form.value.licenseType!,
      businessAddress: this.form.value.businessAddress!,
      files: this.selectedFiles
    }).subscribe({
      next: () => {
        this.router.navigate(['/applications']);
      },
      error: (error) => {
        this.error =
          error?.error?.message ||
          'Failed to submit application.';
        this.loading = false;
      }
    });
  }

}
