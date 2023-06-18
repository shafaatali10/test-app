export interface IMetaData {
  id?: number;
  stidClass?: string | null;
  stidColumnName?: string | null;
  dataLevel?: string | null;
  initialLoadType?: string | null;
  partitionSchema?: string | null;
  orderId?: number | null;
}

export const defaultValue: Readonly<IMetaData> = {};
