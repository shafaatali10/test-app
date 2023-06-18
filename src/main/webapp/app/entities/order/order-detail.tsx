import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order.reducer';

export const OrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderEntity = useAppSelector(state => state.order.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailsHeading">
          <Translate contentKey="testAppApp.order.detail.title">Order</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderEntity.id}</dd>
          <dt>
            <span id="orderType">
              <Translate contentKey="testAppApp.order.orderType">Order Type</Translate>
            </span>
          </dt>
          <dd>{orderEntity.orderType}</dd>
          <dt>
            <span id="requestId">
              <Translate contentKey="testAppApp.order.requestId">Request Id</Translate>
            </span>
          </dt>
          <dd>{orderEntity.requestId}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="testAppApp.order.description">Description</Translate>
            </span>
          </dt>
          <dd>{orderEntity.description}</dd>
          <dt>
            <span id="isApproved">
              <Translate contentKey="testAppApp.order.isApproved">Is Approved</Translate>
            </span>
          </dt>
          <dd>{orderEntity.isApproved ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order/${orderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderDetail;
