import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lookup-category.reducer';

export const LookupCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lookupCategoryEntity = useAppSelector(state => state.lookupCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lookupCategoryDetailsHeading">
          <Translate contentKey="testAppApp.lookupCategory.detail.title">LookupCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lookupCategoryEntity.id}</dd>
          <dt>
            <span id="categoryCode">
              <Translate contentKey="testAppApp.lookupCategory.categoryCode">Category Code</Translate>
            </span>
          </dt>
          <dd>{lookupCategoryEntity.categoryCode}</dd>
        </dl>
        <Button tag={Link} to="/lookup-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lookup-category/${lookupCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LookupCategoryDetail;
