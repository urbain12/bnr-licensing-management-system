import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

import { environment } from '../../../environments/environment';

import { AuthResponse } from '../model/auth-response.model';


@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private api = environment.apiUrl;

  constructor(private http: HttpClient) { }

  login(payload: {
    email: string;
    password: string;
  }): Observable<AuthResponse> {

    return this.http.post<AuthResponse>(
      `${this.api}/auth/login`,
      payload
    ).pipe(

      tap((response) => {

        localStorage.setItem('token', response.token);

        localStorage.setItem(
          'user',
          JSON.stringify(response)
        );

      })
    );
  }


  getUser(): any {
    return JSON.parse(localStorage.getItem('user') || 'null');
  }

  getRole(): string {
    return this.getUser()?.role;
  }

  getPermissions(): string[] {
    const permissions = this.getUser()?.permission;
    return Array.isArray(permissions) ? permissions : [];
  }

  hasPermission(permission: string): boolean {
    return this.getPermissions().includes(permission);
  }

  logout(): void {
    localStorage.clear();
  }
}
