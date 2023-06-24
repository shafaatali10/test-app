import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lookup.reducer';

export const LookupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lookupEntity = useAppSelector(state => state.lookup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lookupDetailsHeading">
          <Translate contentKey="testAppApp.lookup.detail.title">Lookup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.id}</dd>
          <dt>
            <span id="lookupCode">
              <Translate contentKey="testAppApp.lookup.lookupCode">Lookup Code</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.lookupCode}</dd>
          <dt>
            <span id="lookupValue">
              <Translate contentKey="testAppApp.lookup.lookupValue">Lookup Value</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.lookupValue}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="testAppApp.lookup.description">Description</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.description}</dd>
          <dt>
            <span id="displaySequence">
              <Translate contentKey="testAppApp.lookup.displaySequence">Display Sequence</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.displaySequence}</dd>
          <dt>
            <span id="viewName">
              <Translate contentKey="testAppApp.lookup.viewName">View Name</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.viewName}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="testAppApp.lookup.category">Category</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.category}</dd>
          <dt>
            <span id="dependentCode">
              <Translate contentKey="testAppApp.lookup.dependentCode">Dependent Code</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.dependentCode}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="testAppApp.lookup.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{lookupEntity.isActive ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/lookup" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lookup/${lookupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LookupDetail;
