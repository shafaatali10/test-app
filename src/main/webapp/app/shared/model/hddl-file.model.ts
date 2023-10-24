import dayjs from 'dayjs';

export interface IHddlFile {
  id?: number;
  swid?: string | null;
  dbName?: string | null;
  expiryDate?: string | null;
  status?: string | null;
  hddl?: string | null;
}

export const defaultValue: Readonly<IHddlFile> = {};
