import { Moment } from 'moment';

export interface IRequestState {
  id?: number;
  requestId?: number;
  notes?: string;
  status?: string;
  dueDate?: Moment;
}

export const defaultValue: Readonly<IRequestState> = {};
