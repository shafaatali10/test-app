import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LookupCategory from './lookup-category';
import LookupCategoryDetail from './lookup-category-detail';
import LookupCategoryUpdate from './lookup-category-update';
import LookupCategoryDeleteDialog from './lookup-category-delete-dialog';

const LookupCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LookupCategory />} />
    <Route path="new" element={<LookupCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<LookupCategoryDetail />} />
      <Route path="edit" element={<LookupCategoryUpdate />} />
      <Route path="delete" element={<LookupCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LookupCategoryRoutes;
