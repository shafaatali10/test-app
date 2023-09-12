import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RequestState from './request-state';
import RequestStateDetail from './request-state-detail';
import RequestStateUpdate from './request-state-update';
import RequestStateDeleteDialog from './request-state-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RequestStateDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RequestStateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RequestStateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RequestStateDetail} />
      <ErrorBoundaryRoute path={match.url} component={RequestState} />
    </Switch>
  </>
);

export default Routes;
