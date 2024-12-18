import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recipe.reducer';

export const RecipeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recipeEntity = useAppSelector(state => state.recipe.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recipeDetailsHeading">
          <Translate contentKey="repsutApp.recipe.detail.title">Recipe</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recipeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="repsutApp.recipe.name">Name</Translate>
            </span>
          </dt>
          <dd>{recipeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="repsutApp.recipe.description">Description</Translate>
            </span>
          </dt>
          <dd>{recipeEntity.description}</dd>
          <dt>
            <span id="instructions">
              <Translate contentKey="repsutApp.recipe.instructions">Instructions</Translate>
            </span>
          </dt>
          <dd>{recipeEntity.instructions}</dd>
          <dt>
            <span id="cookTime">
              <Translate contentKey="repsutApp.recipe.cookTime">Cook Time</Translate>
            </span>
          </dt>
          <dd>{recipeEntity.cookTime}</dd>
          <dt>
            <span id="servings">
              <Translate contentKey="repsutApp.recipe.servings">Servings</Translate>
            </span>
          </dt>
          <dd>{recipeEntity.servings}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="repsutApp.recipe.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{recipeEntity.createdDate ? <TextFormat value={recipeEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="repsutApp.recipe.category">Category</Translate>
          </dt>
          <dd>
            {recipeEntity.categories
              ? recipeEntity.categories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {recipeEntity.categories && i === recipeEntity.categories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/recipe" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recipe/${recipeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecipeDetail;
