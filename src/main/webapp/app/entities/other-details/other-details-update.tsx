import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOtherDetails } from 'app/shared/model/other-details.model';
import { getEntity, updateEntity, createEntity, reset } from './other-details.reducer';

export const OtherDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const otherDetailsEntity = useAppSelector(state => state.otherDetails.entity);
  const loading = useAppSelector(state => state.otherDetails.loading);
  const updating = useAppSelector(state => state.otherDetails.updating);
  const updateSuccess = useAppSelector(state => state.otherDetails.updateSuccess);

  const handleClose = () => {
    navigate('/other-details');
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
      ...otherDetailsEntity,
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
          ...otherDetailsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testAppApp.otherDetails.home.createOrEditLabel" data-cy="OtherDetailsCreateUpdateHeading">
            <Translate contentKey="testAppApp.otherDetails.home.createOrEditLabel">Create or edit a OtherDetails</Translate>
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
                  id="other-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Mandator Column"
                id="other-details-mandatorColumn"
                name="mandatorColumn"
                data-cy="mandatorColumn"
                type="text"
              />
              <ValidatedField
                label="Is Hub Usage Reqd"
                id="other-details-isHubUsageReqd"
                name="isHubUsageReqd"
                data-cy="isHubUsageReqd"
                check
                type="checkbox"
              />
              <ValidatedField label="Insert Chars" id="other-details-insertChars" name="insertChars" data-cy="insertChars" type="text" />
              <ValidatedField
                label="Table Access Method"
                id="other-details-tableAccessMethod"
                name="tableAccessMethod"
                data-cy="tableAccessMethod"
                type="text"
              />
              <ValidatedField label="One Wmp View" id="other-details-oneWmpView" name="oneWmpView" data-cy="oneWmpView" type="text" />
              <ValidatedField label="Order Id" id="other-details-orderId" name="orderId" data-cy="orderId" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/other-details" replace color="info">
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

export default OtherDetailsUpdate;
