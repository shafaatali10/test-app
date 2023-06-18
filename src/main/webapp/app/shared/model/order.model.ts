export interface IOrder {
  id?: number;
  orderType?: string | null;
  requestId?: number | null;
  description?: string | null;
  isApproved?: boolean | null;
}

export const defaultValue: Readonly<IOrder> = {
  isApproved: false,
};
