import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request.reducer';

export const RequestDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestEntity = useAppSelector(state => state.request.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestDetailsHeading">
          <Translate contentKey="testAppApp.request.detail.title">Request</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requestEntity.id}</dd>
          <dt>
            <span id="rfc">
              <Translate contentKey="testAppApp.request.rfc">Rfc</Translate>
            </span>
          </dt>
          <dd>{requestEntity.rfc}</dd>
          <dt>
            <span id="swid">
              <Translate contentKey="testAppApp.request.swid">Swid</Translate>
            </span>
          </dt>
          <dd>{requestEntity.swid}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="testAppApp.request.status">Status</Translate>
            </span>
          </dt>
          <dd>{requestEntity.status}</dd>
          <dt>
            <span id="isDraft">
              <Translate contentKey="testAppApp.request.isDraft">Is Draft</Translate>
            </span>
          </dt>
          <dd>{requestEntity.isDraft ? 'true' : 'false'}</dd>
          <dt>
            <span id="requestLink">
              <Translate contentKey="testAppApp.request.requestLink">Request Link</Translate>
            </span>
          </dt>
          <dd>{requestEntity.requestLink}</dd>
        </dl>
        <Button tag={Link} to="/request" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request/${requestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestDetail;
