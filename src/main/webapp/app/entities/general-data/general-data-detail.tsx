import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './general-data.reducer';

export const GeneralDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const generalDataEntity = useAppSelector(state => state.generalData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="generalDataDetailsHeading">
          <Translate contentKey="testAppApp.generalData.detail.title">GeneralData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.id}</dd>
          <dt>
            <span id="tableUsage">
              <Translate contentKey="testAppApp.generalData.tableUsage">Table Usage</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.tableUsage}</dd>
          <dt>
            <span id="dbSelection">
              <Translate contentKey="testAppApp.generalData.dbSelection">Db Selection</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.dbSelection}</dd>
          <dt>
            <span id="tableName">
              <Translate contentKey="testAppApp.generalData.tableName">Table Name</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.tableName}</dd>
          <dt>
            <span id="hasDataMoreThan5Million">
              <Translate contentKey="testAppApp.generalData.hasDataMoreThan5Million">Has Data More Than 5 Million</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.hasDataMoreThan5Million ? 'true' : 'false'}</dd>
          <dt>
            <span id="isParallelizationReqd">
              <Translate contentKey="testAppApp.generalData.isParallelizationReqd">Is Parallelization Reqd</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.isParallelizationReqd ? 'true' : 'false'}</dd>
          <dt>
            <span id="recoveryClass">
              <Translate contentKey="testAppApp.generalData.recoveryClass">Recovery Class</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.recoveryClass}</dd>
          <dt>
            <span id="orderId">
              <Translate contentKey="testAppApp.generalData.orderId">Order Id</Translate>
            </span>
          </dt>
          <dd>{generalDataEntity.orderId}</dd>
        </dl>
        <Button tag={Link} to="/general-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/general-data/${generalDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GeneralDataDetail;
