export interface Application {
  id: string;
  name: string;
  referenceNumber: string;
  licenseType: string;
  status: string;
}

export type ApplicationStatus =
  | 'DRAFT'
  | 'SUBMITTED'
  | 'UNDER_REVIEW'
  | 'INFO_REQUESTED'
  | 'RESUBMITTED'
  | 'REVIEW_COMPLETED'
  | 'APPROVED'
  | 'REJECTED';

