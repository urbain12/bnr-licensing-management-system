import { Injectable } from '@angular/core';
import { AuditEntry } from '../model/audit-entry.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Audit } from '../model/audit.model';

@Injectable({
  providedIn: 'root'
})
export class AuditService {

  constructor(private http: HttpClient) { }

  private api = environment.apiUrl

  private auditTrail: AuditEntry[] = [];

  add(entry: AuditEntry): void {

    this.auditTrail.unshift(entry);

  }

  getAll(): AuditEntry[] {
    return this.auditTrail;
  }

  getAuditTrail(applicationId: string) {

  return this.http.get<Audit[]>(
    `${this.api}/applications/${applicationId}/audit-trail`
  );
}
}