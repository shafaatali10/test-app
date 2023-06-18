import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Request from './request';
import Order from './order';
import GeneralData from './general-data';
import MetaData from './meta-data';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="request/*" element={<Request />} />
        <Route path="order/*" element={<Order />} />
        <Route path="general-data/*" element={<GeneralData />} />
        <Route path="meta-data/*" element={<MetaData />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
