export interface IGeneralData {
  id?: number;
  tableUsage?: string | null;
  dbSelection?: string | null;
  tableName?: string | null;
  hasDataMoreThan5Million?: boolean | null;
  isParallelizationReqd?: boolean | null;
  recoveryClass?: string | null;
  orderId?: number | null;
}

export const defaultValue: Readonly<IGeneralData> = {
  hasDataMoreThan5Million: false,
  isParallelizationReqd: false,
};
