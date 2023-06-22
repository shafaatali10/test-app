import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './other-details.reducer';

export const OtherDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const otherDetailsEntity = useAppSelector(state => state.otherDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="otherDetailsDetailsHeading">
          <Translate contentKey="testAppApp.otherDetails.detail.title">OtherDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{otherDetailsEntity.id}</dd>
          <dt>
            <span id="mandatorColumn">
              <Translate contentKey="testAppApp.otherDetails.mandatorColumn">Mandator Column</Translate>
            </span>
          </dt>
          <dd>{otherDetailsEntity.mandatorColumn}</dd>
          <dt>
            <span id="isHubUsageReqd">
              <Translate contentKey="testAppApp.otherDetails.isHubUsageReqd">Is Hub Usage Reqd</Translate>
            </span>
          </dt>
          <dd>{otherDetailsEntity.isHubUsageReqd ? 'true' : 'false'}</dd>
          <dt>
            <span id="insertChars">
              <Translate contentKey="testAppApp.otherDetails.insertChars">Insert Chars</Translate>
            </span>
          </dt>
          <dd>{otherDetailsEntity.insertChars}</dd>
          <dt>
            <span id="tableAccessMethod">
              <Translate contentKey="testAppApp.otherDetails.tableAccessMethod">Table Access Method</Translate>
            </span>
          </dt>
          <dd>{otherDetailsEntity.tableAccessMethod}</dd>
          <dt>
            <span id="oneWmpView">
              <Translate contentKey="testAppApp.otherDetails.oneWmpView">One Wmp View</Translate>
            </span>
          </dt>
          <dd>{otherDetailsEntity.oneWmpView}</dd>
          <dt>
            <span id="orderId">
              <Translate contentKey="testAppApp.otherDetails.orderId">Order Id</Translate>
            </span>
          </dt>
          <dd>{otherDetailsEntity.orderId}</dd>
        </dl>
        <Button tag={Link} to="/other-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/other-details/${otherDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OtherDetailsDetail;
