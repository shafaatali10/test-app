import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './request-state.reducer';
import { IRequestState } from 'app/shared/model/request-state.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRequestStateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const RequestState = (props: IRequestStateProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { requestStateList, match, loading } = props;
  return (
    <div>
      <h2 id="request-state-heading">
        Request States
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Request State
        </Link>
      </h2>
      <div className="table-responsive">
        {requestStateList && requestStateList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Request Id</th>
                <th>Notes</th>
                <th>Status</th>
                <th>Due Date</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {requestStateList.map((requestState, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${requestState.id}`} color="link" size="sm">
                      {requestState.id}
                    </Button>
                  </td>
                  <td>{requestState.requestId}</td>
                  <td>{requestState.notes}</td>
                  <td>{requestState.status}</td>
                  <td>
                    <TextFormat type="date" value={requestState.dueDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${requestState.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${requestState.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${requestState.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Request States found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ requestState }: IRootState) => ({
  requestStateList: requestState.entities,
  loading: requestState.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RequestState);
