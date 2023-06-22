export interface IOtherDetails {
  id?: number;
  mandatorColumn?: string | null;
  isHubUsageReqd?: boolean | null;
  insertChars?: string | null;
  tableAccessMethod?: string | null;
  oneWmpView?: string | null;
  orderId?: number | null;
}

export const defaultValue: Readonly<IOtherDetails> = {
  isHubUsageReqd: false,
};
