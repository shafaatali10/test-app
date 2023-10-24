import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './hddl-file.reducer';

export const HddlFile = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const hddlFileList = useAppSelector(state => state.hddlFile.entities);
  const loading = useAppSelector(state => state.hddlFile.loading);

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
      <h2 id="hddl-file-heading" data-cy="HddlFileHeading">
        <Translate contentKey="testAppApp.hddlFile.home.title">Hddl Files</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testAppApp.hddlFile.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/hddl-file/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testAppApp.hddlFile.home.createLabel">Create new Hddl File</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {hddlFileList && hddlFileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="testAppApp.hddlFile.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('swid')}>
                  <Translate contentKey="testAppApp.hddlFile.swid">Swid</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('swid')} />
                </th>
                <th className="hand" onClick={sort('dbName')}>
                  <Translate contentKey="testAppApp.hddlFile.dbName">Db Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dbName')} />
                </th>
                <th className="hand" onClick={sort('expiryDate')}>
                  <Translate contentKey="testAppApp.hddlFile.expiryDate">Expiry Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expiryDate')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="testAppApp.hddlFile.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('hddl')}>
                  <Translate contentKey="testAppApp.hddlFile.hddl">Hddl</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('hddl')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {hddlFileList.map((hddlFile, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/hddl-file/${hddlFile.id}`} color="link" size="sm">
                      {hddlFile.id}
                    </Button>
                  </td>
                  <td>{hddlFile.swid}</td>
                  <td>{hddlFile.dbName}</td>
                  <td>
                    {hddlFile.expiryDate ? <TextFormat type="date" value={hddlFile.expiryDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{hddlFile.status}</td>
                  <td>{hddlFile.hddl}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/hddl-file/${hddlFile.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hddl-file/${hddlFile.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hddl-file/${hddlFile.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="testAppApp.hddlFile.home.notFound">No Hddl Files found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default HddlFile;
