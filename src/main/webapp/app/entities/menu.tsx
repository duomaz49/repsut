import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate } from 'react-jhipster';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/category">
        <Translate contentKey="global.menu.entities.category" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ingredient">
        <Translate contentKey="global.menu.entities.ingredient" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/recipe">
        <Translate contentKey="global.menu.entities.recipe" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/recipe-to-ingredient">
        <Translate contentKey="global.menu.entities.recipeToIngredient" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
