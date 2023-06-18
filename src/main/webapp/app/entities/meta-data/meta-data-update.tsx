import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMetaData } from 'app/shared/model/meta-data.model';
import { getEntity, updateEntity, createEntity, reset } from './meta-data.reducer';

export const MetaDataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const metaDataEntity = useAppSelector(state => state.metaData.entity);
  const loading = useAppSelector(state => state.metaData.loading);
  const updating = useAppSelector(state => state.metaData.updating);
  const updateSuccess = useAppSelector(state => state.metaData.updateSuccess);

  const handleClose = () => {
    navigate('/meta-data');
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
      ...metaDataEntity,
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
          ...metaDataEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testAppApp.metaData.home.createOrEditLabel" data-cy="MetaDataCreateUpdateHeading">
            <Translate contentKey="testAppApp.metaData.home.createOrEditLabel">Create or edit a MetaData</Translate>
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
                  id="meta-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Stid Class" id="meta-data-stidClass" name="stidClass" data-cy="stidClass" type="text" />
              <ValidatedField
                label="Stid Column Name"
                id="meta-data-stidColumnName"
                name="stidColumnName"
                data-cy="stidColumnName"
                type="text"
              />
              <ValidatedField label="Data Level" id="meta-data-dataLevel" name="dataLevel" data-cy="dataLevel" type="text" />
              <ValidatedField
                label="Initial Load Type"
                id="meta-data-initialLoadType"
                name="initialLoadType"
                data-cy="initialLoadType"
                type="text"
              />
              <ValidatedField
                label="Partition Schema"
                id="meta-data-partitionSchema"
                name="partitionSchema"
                data-cy="partitionSchema"
                type="text"
              />
              <ValidatedField label="Order Id" id="meta-data-orderId" name="orderId" data-cy="orderId" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/meta-data" replace color="info">
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

export default MetaDataUpdate;
