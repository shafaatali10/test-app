import dayjs from 'dayjs';

export interface IRequestState {
  id?: number;
  requestId?: number | null;
  notes?: string | null;
  status?: string | null;
  dueDate?: string | null;
}

export const defaultValue: Readonly<IRequestState> = {};
