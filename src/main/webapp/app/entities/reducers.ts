import request from 'app/entities/request/request.reducer';
import order from 'app/entities/order/order.reducer';
import generalData from 'app/entities/general-data/general-data.reducer';
import metaData from 'app/entities/meta-data/meta-data.reducer';
import otherDetails from 'app/entities/other-details/other-details.reducer';
import department from 'app/entities/department/department.reducer';
import employee from 'app/entities/employee/employee.reducer';
import lookup from 'app/entities/lookup/lookup.reducer';
import requestState from 'app/entities/request-state/request-state.reducer';
import lookupCategory from 'app/entities/lookup-category/lookup-category.reducer';
import hddlFile from 'app/entities/hddl-file/hddl-file.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  request,
  order,
  generalData,
  metaData,
  otherDetails,
  department,
  employee,
  lookup,
  requestState,
  lookupCategory,
  hddlFile,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
