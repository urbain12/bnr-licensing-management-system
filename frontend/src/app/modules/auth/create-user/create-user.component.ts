// create-user.component.ts
import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent {
  loading = false;
  success = '';
  error = '';

  roles = [
    'APPLICANT',
    'REVIEWER',
    'APPROVER',
    'ADMIN'
  ];

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    fullName: ['', Validators.required],
    password: ['', Validators.required],
    role: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) {}

  submit(): void {
    this.success = '';
    this.error = '';

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;

    this.userService.createUser({
      email: this.form.value.email!,
      fullName: this.form.value.fullName!,
      password: this.form.value.password!,
      role: this.form.value.role as any
    }).subscribe({
      next: () => {
        this.success = 'User created successfully.';
        this.form.reset();
      },
      error: (error) => {
        this.error =
          error?.error?.message ||
          'Failed to create user.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}