import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Request from './request';
import Order from './order';
import GeneralData from './general-data';
import MetaData from './meta-data';
import OtherDetails from './other-details';
import Department from './department';
import Employee from './employee';
import Lookup from './lookup';
import RequestState from './request-state';
import LookupCategory from './lookup-category';
import HddlFile from './hddl-file';
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
        <Route path="other-details/*" element={<OtherDetails />} />
        <Route path="department/*" element={<Department />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="lookup/*" element={<Lookup />} />
        <Route path="request-state/*" element={<RequestState />} />
        <Route path="lookup-category/*" element={<LookupCategory />} />
        <Route path="hddl-file/*" element={<HddlFile />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
