import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILookup } from 'app/shared/model/lookup.model';
import { getEntity, updateEntity, createEntity, reset } from './lookup.reducer';

export const LookupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const lookupEntity = useAppSelector(state => state.lookup.entity);
  const loading = useAppSelector(state => state.lookup.loading);
  const updating = useAppSelector(state => state.lookup.updating);
  const updateSuccess = useAppSelector(state => state.lookup.updateSuccess);

  const handleClose = () => {
    navigate('/lookup');
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
      ...lookupEntity,
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
          ...lookupEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testAppApp.lookup.home.createOrEditLabel" data-cy="LookupCreateUpdateHeading">
            <Translate contentKey="testAppApp.lookup.home.createOrEditLabel">Create or edit a Lookup</Translate>
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
                  id="lookup-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Lookup Code" id="lookup-lookupCode" name="lookupCode" data-cy="lookupCode" type="text" />
              <ValidatedField label="Lookup Value" id="lookup-lookupValue" name="lookupValue" data-cy="lookupValue" type="text" />
              <ValidatedField label="Description" id="lookup-description" name="description" data-cy="description" type="text" />
              <ValidatedField
                label="Display Sequence"
                id="lookup-displaySequence"
                name="displaySequence"
                data-cy="displaySequence"
                type="text"
              />
              <ValidatedField label="View Name" id="lookup-viewName" name="viewName" data-cy="viewName" type="text" />
              <ValidatedField label="Category" id="lookup-category" name="category" data-cy="category" type="text" />
              <ValidatedField label="Dependent Code" id="lookup-dependentCode" name="dependentCode" data-cy="dependentCode" type="text" />
              <ValidatedField label="Is Active" id="lookup-isActive" name="isActive" data-cy="isActive" check type="checkbox" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lookup" replace color="info">
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

export default LookupUpdate;
