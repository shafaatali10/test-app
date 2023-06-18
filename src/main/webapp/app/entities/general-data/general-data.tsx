import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './general-data.reducer';

export const GeneralData = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const generalDataList = useAppSelector(state => state.generalData.entities);
  const loading = useAppSelector(state => state.generalData.loading);

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
      <h2 id="general-data-heading" data-cy="GeneralDataHeading">
        <Translate contentKey="testAppApp.generalData.home.title">General Data</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testAppApp.generalData.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/general-data/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testAppApp.generalData.home.createLabel">Create new General Data</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {generalDataList && generalDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="testAppApp.generalData.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('tableUsage')}>
                  <Translate contentKey="testAppApp.generalData.tableUsage">Table Usage</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('tableUsage')} />
                </th>
                <th className="hand" onClick={sort('dbSelection')}>
                  <Translate contentKey="testAppApp.generalData.dbSelection">Db Selection</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dbSelection')} />
                </th>
                <th className="hand" onClick={sort('tableName')}>
                  <Translate contentKey="testAppApp.generalData.tableName">Table Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('tableName')} />
                </th>
                <th className="hand" onClick={sort('hasDataMoreThan5Million')}>
                  <Translate contentKey="testAppApp.generalData.hasDataMoreThan5Million">Has Data More Than 5 Million</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('hasDataMoreThan5Million')} />
                </th>
                <th className="hand" onClick={sort('isParallelizationReqd')}>
                  <Translate contentKey="testAppApp.generalData.isParallelizationReqd">Is Parallelization Reqd</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isParallelizationReqd')} />
                </th>
                <th className="hand" onClick={sort('recoveryClass')}>
                  <Translate contentKey="testAppApp.generalData.recoveryClass">Recovery Class</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('recoveryClass')} />
                </th>
                <th className="hand" onClick={sort('orderId')}>
                  <Translate contentKey="testAppApp.generalData.orderId">Order Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('orderId')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {generalDataList.map((generalData, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/general-data/${generalData.id}`} color="link" size="sm">
                      {generalData.id}
                    </Button>
                  </td>
                  <td>{generalData.tableUsage}</td>
                  <td>{generalData.dbSelection}</td>
                  <td>{generalData.tableName}</td>
                  <td>{generalData.hasDataMoreThan5Million ? 'true' : 'false'}</td>
                  <td>{generalData.isParallelizationReqd ? 'true' : 'false'}</td>
                  <td>{generalData.recoveryClass}</td>
                  <td>{generalData.orderId}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/general-data/${generalData.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/general-data/${generalData.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/general-data/${generalData.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="testAppApp.generalData.home.notFound">No General Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default GeneralData;
