import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';

import { Application } from '../model/application.model';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  private api = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getApplications(): Observable<Application[]> {

    return this.http.get<Application[]>(
      `${this.api}/applications`
    );
  }

  getApplication(id: string): Observable<Application> {

    return this.http.get<Application>(
      `${this.api}/applications/${id}`
    );
  }

   createApplication(payload: {
    institutionName: string;
    licenseType: string;
    businessAddress: string;
    files: {
      MARKET_RISK_MANAGEMENT_POLICY: File;
      CLIENT_ASSET_PROTECTION_POLICY: File;
      TRADING_POLICY: File;
    };
  }): Observable<Application> {
    const formData = new FormData();

    formData.append(
      'MARKET_RISK_MANAGEMENT_POLICY',
      payload.files.MARKET_RISK_MANAGEMENT_POLICY
    );

    formData.append(
      'CLIENT_ASSET_PROTECTION_POLICY',
      payload.files.CLIENT_ASSET_PROTECTION_POLICY
    );

    formData.append(
      'TRADING_POLICY',
      payload.files.TRADING_POLICY
    );

    const params = new HttpParams()
      .set('institutionName', payload.institutionName)
      .set('licenseType', payload.licenseType)
      .set('businessAddress', payload.businessAddress);

    return this.http.post<Application>(
      `${this.api}/applications`,
      formData,
      { params }
    );
  }

  startReview(id: string) {

    return this.http.post(
      `${this.api}/review/${id}/start`,
      {}
    );
  }

  completeReview(id: string, comment: string) {

    return this.http.post(
      `${this.api}/review/${id}/complete`,
      { comment }
    );
  }

  requestInfo(id: string, comment: string) {

    return this.http.post(
      `${this.api}/review/${id}/request-info`,
      { comment }
    );
  }

  approve(id: string, comment: string) {

    return this.http.post(
      `${this.api}/approval/${id}/approve`,
      { comment }
    );
  }

  reject(id: string, comment: string) {

    return this.http.post(
      `${this.api}/approval/${id}/reject`,
      { comment }
    );
  }
}