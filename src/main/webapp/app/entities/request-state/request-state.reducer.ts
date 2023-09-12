import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRequestState, defaultValue } from 'app/shared/model/request-state.model';

export const ACTION_TYPES = {
  FETCH_REQUESTSTATE_LIST: 'requestState/FETCH_REQUESTSTATE_LIST',
  FETCH_REQUESTSTATE: 'requestState/FETCH_REQUESTSTATE',
  CREATE_REQUESTSTATE: 'requestState/CREATE_REQUESTSTATE',
  UPDATE_REQUESTSTATE: 'requestState/UPDATE_REQUESTSTATE',
  DELETE_REQUESTSTATE: 'requestState/DELETE_REQUESTSTATE',
  RESET: 'requestState/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRequestState>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RequestStateState = Readonly<typeof initialState>;

// Reducer

export default (state: RequestStateState = initialState, action): RequestStateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REQUESTSTATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REQUESTSTATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_REQUESTSTATE):
    case REQUEST(ACTION_TYPES.UPDATE_REQUESTSTATE):
    case REQUEST(ACTION_TYPES.DELETE_REQUESTSTATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_REQUESTSTATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REQUESTSTATE):
    case FAILURE(ACTION_TYPES.CREATE_REQUESTSTATE):
    case FAILURE(ACTION_TYPES.UPDATE_REQUESTSTATE):
    case FAILURE(ACTION_TYPES.DELETE_REQUESTSTATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_REQUESTSTATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_REQUESTSTATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_REQUESTSTATE):
    case SUCCESS(ACTION_TYPES.UPDATE_REQUESTSTATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_REQUESTSTATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/request-states';

// Actions

export const getEntities: ICrudGetAllAction<IRequestState> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_REQUESTSTATE_LIST,
  payload: axios.get<IRequestState>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRequestState> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REQUESTSTATE,
    payload: axios.get<IRequestState>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRequestState> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_REQUESTSTATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRequestState> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_REQUESTSTATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRequestState> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_REQUESTSTATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
