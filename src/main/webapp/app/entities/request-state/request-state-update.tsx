import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRequestState } from 'app/shared/model/request-state.model';
import { getEntity, updateEntity, createEntity, reset } from './request-state.reducer';

export const RequestStateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const requestStateEntity = useAppSelector(state => state.requestState.entity);
  const loading = useAppSelector(state => state.requestState.loading);
  const updating = useAppSelector(state => state.requestState.updating);
  const updateSuccess = useAppSelector(state => state.requestState.updateSuccess);

  const handleClose = () => {
    navigate('/request-state');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...requestStateEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...requestStateEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testAppApp.requestState.home.createOrEditLabel" data-cy="RequestStateCreateUpdateHeading">
            <Translate contentKey="testAppApp.requestState.home.createOrEditLabel">Create or edit a RequestState</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="request-state-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Request Id" id="request-state-requestId" name="requestId" data-cy="requestId" type="text" />
              <ValidatedField label="Notes" id="request-state-notes" name="notes" data-cy="notes" type="text" />
              <ValidatedField label="Status" id="request-state-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Due Date" id="request-state-dueDate" name="dueDate" data-cy="dueDate" type="date" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/request-state" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RequestStateUpdate;
