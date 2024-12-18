import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recipe-to-ingredient.reducer';

export const RecipeToIngredientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recipeToIngredientEntity = useAppSelector(state => state.recipeToIngredient.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recipeToIngredientDetailsHeading">
          <Translate contentKey="repsutApp.recipeToIngredient.detail.title">RecipeToIngredient</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recipeToIngredientEntity.id}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="repsutApp.recipeToIngredient.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{recipeToIngredientEntity.quantity}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="repsutApp.recipeToIngredient.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{recipeToIngredientEntity.unit}</dd>
          <dt>
            <Translate contentKey="repsutApp.recipeToIngredient.ingredient">Ingredient</Translate>
          </dt>
          <dd>{recipeToIngredientEntity.ingredient ? recipeToIngredientEntity.ingredient.id : ''}</dd>
          <dt>
            <Translate contentKey="repsutApp.recipeToIngredient.recipe">Recipe</Translate>
          </dt>
          <dd>{recipeToIngredientEntity.recipe ? recipeToIngredientEntity.recipe.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/recipe-to-ingredient" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recipe-to-ingredient/${recipeToIngredientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecipeToIngredientDetail;
