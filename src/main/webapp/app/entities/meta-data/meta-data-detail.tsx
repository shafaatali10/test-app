import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meta-data.reducer';

export const MetaDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const metaDataEntity = useAppSelector(state => state.metaData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="metaDataDetailsHeading">
          <Translate contentKey="testAppApp.metaData.detail.title">MetaData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{metaDataEntity.id}</dd>
          <dt>
            <span id="stidClass">
              <Translate contentKey="testAppApp.metaData.stidClass">Stid Class</Translate>
            </span>
          </dt>
          <dd>{metaDataEntity.stidClass}</dd>
          <dt>
            <span id="stidColumnName">
              <Translate contentKey="testAppApp.metaData.stidColumnName">Stid Column Name</Translate>
            </span>
          </dt>
          <dd>{metaDataEntity.stidColumnName}</dd>
          <dt>
            <span id="dataLevel">
              <Translate contentKey="testAppApp.metaData.dataLevel">Data Level</Translate>
            </span>
          </dt>
          <dd>{metaDataEntity.dataLevel}</dd>
          <dt>
            <span id="initialLoadType">
              <Translate contentKey="testAppApp.metaData.initialLoadType">Initial Load Type</Translate>
            </span>
          </dt>
          <dd>{metaDataEntity.initialLoadType}</dd>
          <dt>
            <span id="partitionSchema">
              <Translate contentKey="testAppApp.metaData.partitionSchema">Partition Schema</Translate>
            </span>
          </dt>
          <dd>{metaDataEntity.partitionSchema}</dd>
          <dt>
            <span id="orderId">
              <Translate contentKey="testAppApp.metaData.orderId">Order Id</Translate>
            </span>
          </dt>
          <dd>{metaDataEntity.orderId}</dd>
        </dl>
        <Button tag={Link} to="/meta-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meta-data/${metaDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MetaDataDetail;
