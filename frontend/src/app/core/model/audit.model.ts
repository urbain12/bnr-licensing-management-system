export interface Audit {
  actor: string;
  action: string;
  previousState: string;
  newState: string;
  timestamp: string;
  comment: string;
}