import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGeneralData } from 'app/shared/model/general-data.model';
import { getEntity, updateEntity, createEntity, reset } from './general-data.reducer';

export const GeneralDataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const generalDataEntity = useAppSelector(state => state.generalData.entity);
  const loading = useAppSelector(state => state.generalData.loading);
  const updating = useAppSelector(state => state.generalData.updating);
  const updateSuccess = useAppSelector(state => state.generalData.updateSuccess);

  const handleClose = () => {
    navigate('/general-data');
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
      ...generalDataEntity,
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
          ...generalDataEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testAppApp.generalData.home.createOrEditLabel" data-cy="GeneralDataCreateUpdateHeading">
            <Translate contentKey="testAppApp.generalData.home.createOrEditLabel">Create or edit a GeneralData</Translate>
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
                  id="general-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Table Usage" id="general-data-tableUsage" name="tableUsage" data-cy="tableUsage" type="text" />
              <ValidatedField label="Db Selection" id="general-data-dbSelection" name="dbSelection" data-cy="dbSelection" type="text" />
              <ValidatedField label="Table Name" id="general-data-tableName" name="tableName" data-cy="tableName" type="text" />
              <ValidatedField
                label="Has Data More Than 5 Million"
                id="general-data-hasDataMoreThan5Million"
                name="hasDataMoreThan5Million"
                data-cy="hasDataMoreThan5Million"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Is Parallelization Reqd"
                id="general-data-isParallelizationReqd"
                name="isParallelizationReqd"
                data-cy="isParallelizationReqd"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Recovery Class"
                id="general-data-recoveryClass"
                name="recoveryClass"
                data-cy="recoveryClass"
                type="text"
              />
              <ValidatedField label="Order Id" id="general-data-orderId" name="orderId" data-cy="orderId" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/general-data" replace color="info">
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

export default GeneralDataUpdate;
