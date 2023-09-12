import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './request-state.reducer';
import { IRequestState } from 'app/shared/model/request-state.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRequestStateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RequestStateUpdate = (props: IRequestStateUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { requestStateEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/request-state');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...requestStateEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testAppApp.requestState.home.createOrEditLabel">Create or edit a RequestState</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : requestStateEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="request-state-id">ID</Label>
                  <AvInput id="request-state-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="requestIdLabel" for="request-state-requestId">
                  Request Id
                </Label>
                <AvField id="request-state-requestId" type="string" className="form-control" name="requestId" />
              </AvGroup>
              <AvGroup>
                <Label id="notesLabel" for="request-state-notes">
                  Notes
                </Label>
                <AvField id="request-state-notes" type="text" name="notes" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="request-state-status">
                  Status
                </Label>
                <AvField id="request-state-status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="dueDateLabel" for="request-state-dueDate">
                  Due Date
                </Label>
                <AvField id="request-state-dueDate" type="date" className="form-control" name="dueDate" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/request-state" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  requestStateEntity: storeState.requestState.entity,
  loading: storeState.requestState.loading,
  updating: storeState.requestState.updating,
  updateSuccess: storeState.requestState.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RequestStateUpdate);
