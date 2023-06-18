import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MetaData from './meta-data';
import MetaDataDetail from './meta-data-detail';
import MetaDataUpdate from './meta-data-update';
import MetaDataDeleteDialog from './meta-data-delete-dialog';

const MetaDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MetaData />} />
    <Route path="new" element={<MetaDataUpdate />} />
    <Route path=":id">
      <Route index element={<MetaDataDetail />} />
      <Route path="edit" element={<MetaDataUpdate />} />
      <Route path="delete" element={<MetaDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MetaDataRoutes;
