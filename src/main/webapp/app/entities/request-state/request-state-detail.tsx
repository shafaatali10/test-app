import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './request-state.reducer';
import { IRequestState } from 'app/shared/model/request-state.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRequestStateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RequestStateDetail = (props: IRequestStateDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { requestStateEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          RequestState [<b>{requestStateEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="requestId">Request Id</span>
          </dt>
          <dd>{requestStateEntity.requestId}</dd>
          <dt>
            <span id="notes">Notes</span>
          </dt>
          <dd>{requestStateEntity.notes}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{requestStateEntity.status}</dd>
          <dt>
            <span id="dueDate">Due Date</span>
          </dt>
          <dd>
            <TextFormat value={requestStateEntity.dueDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
          </dd>
        </dl>
        <Button tag={Link} to="/request-state" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-state/${requestStateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ requestState }: IRootState) => ({
  requestStateEntity: requestState.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RequestStateDetail);
