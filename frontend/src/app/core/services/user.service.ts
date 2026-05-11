// src/app/core/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { CreateUserRequest, UserResponse } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  createUser(payload: CreateUserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(`${this.api}/users`, payload);
  }
}