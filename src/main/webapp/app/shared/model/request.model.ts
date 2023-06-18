export interface IRequest {
  id?: number;
  rfc?: string | null;
  swid?: string | null;
  status?: string | null;
  isDraft?: boolean | null;
  requestLink?: string | null;
}

export const defaultValue: Readonly<IRequest> = {
  isDraft: false,
};
