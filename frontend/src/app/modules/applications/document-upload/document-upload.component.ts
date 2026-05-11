import { Component, EventEmitter, Output } from '@angular/core';

type DocumentCode =
  | 'MARKET_RISK_MANAGEMENT_POLICY'
  | 'CLIENT_ASSET_PROTECTION_POLICY'
  | 'TRADING_POLICY';

interface RequiredDocument {
  code: DocumentCode;
  label: string;
  file?: File;
  error?: string;
}

@Component({
  selector: 'app-document-upload',
  templateUrl: './document-upload.component.html',
  styleUrls: ['./document-upload.component.scss']
})
export class DocumentUploadComponent {

 @Output() filesChanged = new EventEmitter<Record<DocumentCode, File>>();

  maxFileSize = 5 * 1024 * 1024;

  allowedTypes = [
    'application/pdf',
    'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
  ];

  documents: RequiredDocument[] = [
    {
      code: 'MARKET_RISK_MANAGEMENT_POLICY',
      label: 'Market Risk Management Policy'
    },
    {
      code: 'CLIENT_ASSET_PROTECTION_POLICY',
      label: 'Client Asset Protection Policy'
    },
    {
      code: 'TRADING_POLICY',
      label: 'Trading Policy'
    }
  ];

  onFileSelected(event: Event, doc: RequiredDocument): void {
    const input = event.target as HTMLInputElement;

    if (!input.files?.length) return;

    this.setFile(input.files[0], doc);
    input.value = '';
  }

  onDrop(event: DragEvent, doc: RequiredDocument): void {
    event.preventDefault();

    const file = event.dataTransfer?.files?.[0];

    if (!file) return;

    this.setFile(file, doc);
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
  }

  private setFile(file: File, doc: RequiredDocument): void {
    doc.error = '';

    if (!this.allowedTypes.includes(file.type)) {
      doc.file = undefined;
      doc.error = 'Only PDF, DOC, or DOCX files are allowed.';
      this.emitFiles();
      return;
    }

    if (file.size > this.maxFileSize) {
      doc.file = undefined;
      doc.error = 'File must not exceed 5MB.';
      this.emitFiles();
      return;
    }

    doc.file = file;
    this.emitFiles();
  }

  removeFile(doc: RequiredDocument): void {
    doc.file = undefined;
    doc.error = '';
    this.emitFiles();
  }

  isComplete(): boolean {
    return this.documents.every(doc => !!doc.file);
  }

  getFileSize(size: number): string {
    return `${(size / 1024 / 1024).toFixed(2)} MB`;
  }

  private emitFiles(): void {
    const selected: Partial<Record<DocumentCode, File>> = {};

    this.documents.forEach(doc => {
      if (doc.file) {
        selected[doc.code] = doc.file;
      }
    });

    this.filesChanged.emit(selected as Record<DocumentCode, File>);
  }
}