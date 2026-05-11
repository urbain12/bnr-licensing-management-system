import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {


  loading = false;

  error = '';

  credentials = {
    email: '',
    password: ''
  };

  constructor(
    private auth: AuthService,
    private router: Router
  ) { }


  login(): void {
  this.loading = true;
  this.error = '';

  this.auth.login(this.credentials).subscribe({
    next: (response) => {
      console.log('Login response:', response);

      const allowedRoles = ['REVIEWER', 'APPLICANT', 'ADMIN', 'APPROVER'];

      if (response?.token && allowedRoles.includes(response.role)) {
        this.router.navigateByUrl('/applications');
      } else {
        this.error = 'Unauthorized role';
        this.router.navigateByUrl('/auth/login');
      }
    },

    error: (error) => {
      this.error =
        error?.error?.message ||
        'Invalid credentials';

      this.loading = false;
    },

    complete: () => {
      this.loading = false;
    }
  });
}

}
