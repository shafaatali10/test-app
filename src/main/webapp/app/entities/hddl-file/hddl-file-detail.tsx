import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hddl-file.reducer';

export const HddlFileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hddlFileEntity = useAppSelector(state => state.hddlFile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hddlFileDetailsHeading">
          <Translate contentKey="testAppApp.hddlFile.detail.title">HddlFile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hddlFileEntity.id}</dd>
          <dt>
            <span id="swid">
              <Translate contentKey="testAppApp.hddlFile.swid">Swid</Translate>
            </span>
          </dt>
          <dd>{hddlFileEntity.swid}</dd>
          <dt>
            <span id="dbName">
              <Translate contentKey="testAppApp.hddlFile.dbName">Db Name</Translate>
            </span>
          </dt>
          <dd>{hddlFileEntity.dbName}</dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="testAppApp.hddlFile.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {hddlFileEntity.expiryDate ? <TextFormat value={hddlFileEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="testAppApp.hddlFile.status">Status</Translate>
            </span>
          </dt>
          <dd>{hddlFileEntity.status}</dd>
          <dt>
            <span id="hddl">
              <Translate contentKey="testAppApp.hddlFile.hddl">Hddl</Translate>
            </span>
          </dt>
          <dd>{hddlFileEntity.hddl}</dd>
        </dl>
        <Button tag={Link} to="/hddl-file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hddl-file/${hddlFileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HddlFileDetail;
