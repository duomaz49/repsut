import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="../../../content/images/Repsut.png" alt="Logo" />
  </div>
);

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="navbar-version">{VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`}</span>
  </NavbarBrand>
);

export const Home = () => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" className="me-1" />
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const RandomRecipe = () => {
  return (
    <NavItem>
      <NavLink tag={Link} to="/random-recipe" className="d-flex align-items-center">
        <span>
          <Translate contentKey="global.menu.randomRecipe">Recipe Inspiration</Translate>
        </span>
      </NavLink>
    </NavItem>
  );
};

export const SearchRecipe = () => {
  return (
    <NavItem>
      <NavLink tag={Link} to="/search-recipe" className="d-flex align-items-center">
        <span>
          <Translate contentKey="global.menu.searchRecipe">Search for recipe</Translate>
        </span>
      </NavLink>
    </NavItem>
  );
};

export const OwnRecipes = () => {
  return (
    <NavItem>
      <NavLink tag={Link} to="/own-recipes" className="d-flex align-items-center">
        <span>
          <Translate contentKey="global.menu.ownRecipes">Own recipes</Translate>
        </span>
      </NavLink>
    </NavItem>
  );
};
