import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './employee.reducer';

export const Employee = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const employeeList = useAppSelector(state => state.employee.entities);
  const loading = useAppSelector(state => state.employee.loading);

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
      <h2 id="employee-heading" data-cy="EmployeeHeading">
        <Translate contentKey="testAppApp.employee.home.title">Employees</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testAppApp.employee.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/employee/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testAppApp.employee.home.createLabel">Create new Employee</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeList && employeeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="testAppApp.employee.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="testAppApp.employee.firstName">First Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('firstName')} />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="testAppApp.employee.lastName">Last Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastName')} />
                </th>
                <th className="hand" onClick={sort('salary')}>
                  <Translate contentKey="testAppApp.employee.salary">Salary</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('salary')} />
                </th>
                <th className="hand" onClick={sort('deptId')}>
                  <Translate contentKey="testAppApp.employee.deptId">Dept Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deptId')} />
                </th>
                <th>
                  <Translate contentKey="testAppApp.employee.department">Department</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeList.map((employee, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee/${employee.id}`} color="link" size="sm">
                      {employee.id}
                    </Button>
                  </td>
                  <td>{employee.firstName}</td>
                  <td>{employee.lastName}</td>
                  <td>{employee.salary}</td>
                  <td>{employee.deptId}</td>
                  <td>{employee.department ? <Link to={`/department/${employee.department.id}`}>{employee.department.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/employee/${employee.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/employee/${employee.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/employee/${employee.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="testAppApp.employee.home.notFound">No Employees found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Employee;
