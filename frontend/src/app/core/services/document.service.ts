import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { DocumentFile } from '../model/document.model';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(
    private http: HttpClient
  ) { }

  private api = environment.apiUrl

  getDocuments(applicationId: string) {

  return this.http.get<DocumentFile[]>(
    `${this.api}/applications/${applicationId}/documents`
  );
}
}
