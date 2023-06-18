import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './meta-data.reducer';

export const MetaData = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const metaDataList = useAppSelector(state => state.metaData.entities);
  const loading = useAppSelector(state => state.metaData.loading);

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
      <h2 id="meta-data-heading" data-cy="MetaDataHeading">
        <Translate contentKey="testAppApp.metaData.home.title">Meta Data</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testAppApp.metaData.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/meta-data/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testAppApp.metaData.home.createLabel">Create new Meta Data</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {metaDataList && metaDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="testAppApp.metaData.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('stidClass')}>
                  <Translate contentKey="testAppApp.metaData.stidClass">Stid Class</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('stidClass')} />
                </th>
                <th className="hand" onClick={sort('stidColumnName')}>
                  <Translate contentKey="testAppApp.metaData.stidColumnName">Stid Column Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('stidColumnName')} />
                </th>
                <th className="hand" onClick={sort('dataLevel')}>
                  <Translate contentKey="testAppApp.metaData.dataLevel">Data Level</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dataLevel')} />
                </th>
                <th className="hand" onClick={sort('initialLoadType')}>
                  <Translate contentKey="testAppApp.metaData.initialLoadType">Initial Load Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('initialLoadType')} />
                </th>
                <th className="hand" onClick={sort('partitionSchema')}>
                  <Translate contentKey="testAppApp.metaData.partitionSchema">Partition Schema</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('partitionSchema')} />
                </th>
                <th className="hand" onClick={sort('orderId')}>
                  <Translate contentKey="testAppApp.metaData.orderId">Order Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('orderId')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {metaDataList.map((metaData, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/meta-data/${metaData.id}`} color="link" size="sm">
                      {metaData.id}
                    </Button>
                  </td>
                  <td>{metaData.stidClass}</td>
                  <td>{metaData.stidColumnName}</td>
                  <td>{metaData.dataLevel}</td>
                  <td>{metaData.initialLoadType}</td>
                  <td>{metaData.partitionSchema}</td>
                  <td>{metaData.orderId}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/meta-data/${metaData.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/meta-data/${metaData.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/meta-data/${metaData.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="testAppApp.metaData.home.notFound">No Meta Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MetaData;
