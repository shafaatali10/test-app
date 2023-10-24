import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/request">
        Request
      </MenuItem>
      <MenuItem icon="asterisk" to="/order">
        Order
      </MenuItem>
      <MenuItem icon="asterisk" to="/general-data">
        General Data
      </MenuItem>
      <MenuItem icon="asterisk" to="/meta-data">
        Meta Data
      </MenuItem>
      <MenuItem icon="asterisk" to="/other-details">
        Other Details
      </MenuItem>
      <MenuItem icon="asterisk" to="/department">
        Department
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        Employee
      </MenuItem>
      <MenuItem icon="asterisk" to="/lookup">
        Lookup
      </MenuItem>
      <MenuItem icon="asterisk" to="/request-state">
        Request State
      </MenuItem>
      <MenuItem icon="asterisk" to="/lookup-category">
        Lookup Category
      </MenuItem>
      <MenuItem icon="asterisk" to="/hddl-file">
        Hddl File
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
