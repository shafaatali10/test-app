import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lookup from './lookup';
import LookupDetail from './lookup-detail';
import LookupUpdate from './lookup-update';
import LookupDeleteDialog from './lookup-delete-dialog';

const LookupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Lookup />} />
    <Route path="new" element={<LookupUpdate />} />
    <Route path=":id">
      <Route index element={<LookupDetail />} />
      <Route path="edit" element={<LookupUpdate />} />
      <Route path="delete" element={<LookupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LookupRoutes;
