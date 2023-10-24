import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestState from './request-state';
import RequestStateDetail from './request-state-detail';
import RequestStateUpdate from './request-state-update';
import RequestStateDeleteDialog from './request-state-delete-dialog';

const RequestStateRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestState />} />
    <Route path="new" element={<RequestStateUpdate />} />
    <Route path=":id">
      <Route index element={<RequestStateDetail />} />
      <Route path="edit" element={<RequestStateUpdate />} />
      <Route path="delete" element={<RequestStateDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequestStateRoutes;
