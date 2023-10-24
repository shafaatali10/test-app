import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHddlFile } from 'app/shared/model/hddl-file.model';
import { getEntity, updateEntity, createEntity, reset } from './hddl-file.reducer';

export const HddlFileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hddlFileEntity = useAppSelector(state => state.hddlFile.entity);
  const loading = useAppSelector(state => state.hddlFile.loading);
  const updating = useAppSelector(state => state.hddlFile.updating);
  const updateSuccess = useAppSelector(state => state.hddlFile.updateSuccess);

  const handleClose = () => {
    navigate('/hddl-file');
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
      ...hddlFileEntity,
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
          ...hddlFileEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testAppApp.hddlFile.home.createOrEditLabel" data-cy="HddlFileCreateUpdateHeading">
            <Translate contentKey="testAppApp.hddlFile.home.createOrEditLabel">Create or edit a HddlFile</Translate>
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
                  id="hddl-file-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Swid" id="hddl-file-swid" name="swid" data-cy="swid" type="text" />
              <ValidatedField label="Db Name" id="hddl-file-dbName" name="dbName" data-cy="dbName" type="text" />
              <ValidatedField label="Expiry Date" id="hddl-file-expiryDate" name="expiryDate" data-cy="expiryDate" type="date" />
              <ValidatedField label="Status" id="hddl-file-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Hddl" id="hddl-file-hddl" name="hddl" data-cy="hddl" type="textarea" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hddl-file" replace color="info">
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

export default HddlFileUpdate;
