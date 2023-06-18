import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GeneralData from './general-data';
import GeneralDataDetail from './general-data-detail';
import GeneralDataUpdate from './general-data-update';
import GeneralDataDeleteDialog from './general-data-delete-dialog';

const GeneralDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GeneralData />} />
    <Route path="new" element={<GeneralDataUpdate />} />
    <Route path=":id">
      <Route index element={<GeneralDataDetail />} />
      <Route path="edit" element={<GeneralDataUpdate />} />
      <Route path="delete" element={<GeneralDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GeneralDataRoutes;
