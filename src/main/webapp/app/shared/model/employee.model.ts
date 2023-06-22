import { IDepartment } from 'app/shared/model/department.model';

export interface IEmployee {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  salary?: number | null;
  deptId?: number | null;
  department?: IDepartment | null;
}

export const defaultValue: Readonly<IEmployee> = {};
