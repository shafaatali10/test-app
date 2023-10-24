import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HddlFile from './hddl-file';
import HddlFileDetail from './hddl-file-detail';
import HddlFileUpdate from './hddl-file-update';
import HddlFileDeleteDialog from './hddl-file-delete-dialog';

const HddlFileRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HddlFile />} />
    <Route path="new" element={<HddlFileUpdate />} />
    <Route path=":id">
      <Route index element={<HddlFileDetail />} />
      <Route path="edit" element={<HddlFileUpdate />} />
      <Route path="delete" element={<HddlFileDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HddlFileRoutes;
