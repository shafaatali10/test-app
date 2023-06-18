import request from 'app/entities/request/request.reducer';
import order from 'app/entities/order/order.reducer';
import generalData from 'app/entities/general-data/general-data.reducer';
import metaData from 'app/entities/meta-data/meta-data.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  request,
  order,
  generalData,
  metaData,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
