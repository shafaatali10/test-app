import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request-state.reducer';

export const RequestStateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestStateEntity = useAppSelector(state => state.requestState.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestStateDetailsHeading">
          <Translate contentKey="testAppApp.requestState.detail.title">RequestState</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requestStateEntity.id}</dd>
          <dt>
            <span id="requestId">
              <Translate contentKey="testAppApp.requestState.requestId">Request Id</Translate>
            </span>
          </dt>
          <dd>{requestStateEntity.requestId}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="testAppApp.requestState.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{requestStateEntity.notes}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="testAppApp.requestState.status">Status</Translate>
            </span>
          </dt>
          <dd>{requestStateEntity.status}</dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="testAppApp.requestState.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>
            {requestStateEntity.dueDate ? (
              <TextFormat value={requestStateEntity.dueDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/request-state" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-state/${requestStateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestStateDetail;
