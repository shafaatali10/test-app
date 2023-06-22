import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OtherDetails from './other-details';
import OtherDetailsDetail from './other-details-detail';
import OtherDetailsUpdate from './other-details-update';
import OtherDetailsDeleteDialog from './other-details-delete-dialog';

const OtherDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OtherDetails />} />
    <Route path="new" element={<OtherDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<OtherDetailsDetail />} />
      <Route path="edit" element={<OtherDetailsUpdate />} />
      <Route path="delete" element={<OtherDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OtherDetailsRoutes;
