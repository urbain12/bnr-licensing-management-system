// src/app/core/models/user.model.ts
export interface CreateUserRequest {
  email: string;
  fullName: string;
  password: string;
  role: 'APPLICANT' | 'REVIEWER' | 'APPROVER' | 'ADMIN';
}

export interface UserResponse {
  id: string;
  email: string;
  fullName: string;
  role: string;
}