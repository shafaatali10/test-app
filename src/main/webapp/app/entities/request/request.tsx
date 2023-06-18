import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './request.reducer';

export const Request = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const requestList = useAppSelector(state => state.request.entities);
  const loading = useAppSelector(state => state.request.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="request-heading" data-cy="RequestHeading">
        <Translate contentKey="testAppApp.request.home.title">Requests</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testAppApp.request.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/request/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testAppApp.request.home.createLabel">Create new Request</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {requestList && requestList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="testAppApp.request.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('rfc')}>
                  <Translate contentKey="testAppApp.request.rfc">Rfc</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('rfc')} />
                </th>
                <th className="hand" onClick={sort('swid')}>
                  <Translate contentKey="testAppApp.request.swid">Swid</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('swid')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="testAppApp.request.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('isDraft')}>
                  <Translate contentKey="testAppApp.request.isDraft">Is Draft</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isDraft')} />
                </th>
                <th className="hand" onClick={sort('requestLink')}>
                  <Translate contentKey="testAppApp.request.requestLink">Request Link</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('requestLink')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {requestList.map((request, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/request/${request.id}`} color="link" size="sm">
                      {request.id}
                    </Button>
                  </td>
                  <td>{request.rfc}</td>
                  <td>{request.swid}</td>
                  <td>{request.status}</td>
                  <td>{request.isDraft ? 'true' : 'false'}</td>
                  <td>{request.requestLink}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/request/${request.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/request/${request.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/request/${request.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="testAppApp.request.home.notFound">No Requests found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Request;
