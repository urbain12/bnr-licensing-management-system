export interface AuditEntry {

  action: string;

  actor: string;

  timestamp: string;

  previousState: string;

  newState: string;

  comment?: string;
}