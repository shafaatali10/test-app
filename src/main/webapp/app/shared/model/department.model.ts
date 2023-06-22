import { IEmployee } from 'app/shared/model/employee.model';

export interface IDepartment {
  id?: number;
  deptCode?: string | null;
  deptName?: string | null;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<IDepartment> = {};
