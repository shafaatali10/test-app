export interface ILookup {
  id?: number;
  lookupCode?: string | null;
  lookupValue?: string | null;
  description?: string | null;
  displaySequence?: number | null;
  viewName?: string | null;
  category?: string | null;
  dependentCode?: string | null;
  isActive?: boolean | null;
}

export const defaultValue: Readonly<ILookup> = {
  isActive: false,
};
