import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getIngredients } from 'app/entities/ingredient/ingredient.reducer';
import { getEntities as getRecipes } from 'app/entities/recipe/recipe.reducer';
import { Unit } from 'app/shared/model/enumerations/unit.model';
import { createEntity, getEntity, reset, updateEntity } from './recipe-to-ingredient.reducer';

export const RecipeToIngredientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ingredients = useAppSelector(state => state.ingredient.entities);
  const recipes = useAppSelector(state => state.recipe.entities);
  const recipeToIngredientEntity = useAppSelector(state => state.recipeToIngredient.entity);
  const loading = useAppSelector(state => state.recipeToIngredient.loading);
  const updating = useAppSelector(state => state.recipeToIngredient.updating);
  const updateSuccess = useAppSelector(state => state.recipeToIngredient.updateSuccess);
  const unitValues = Object.keys(Unit);

  const handleClose = () => {
    navigate('/recipe-to-ingredient');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getIngredients({}));
    dispatch(getRecipes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.quantity !== undefined && typeof values.quantity !== 'number') {
      values.quantity = Number(values.quantity);
    }

    const entity = {
      ...recipeToIngredientEntity,
      ...values,
      ingredient: ingredients.find(it => it.id.toString() === values.ingredient?.toString()),
      recipe: recipes.find(it => it.id.toString() === values.recipe?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          unit: 'MILLILITER',
          ...recipeToIngredientEntity,
          ingredient: recipeToIngredientEntity?.ingredient?.id,
          recipe: recipeToIngredientEntity?.recipe?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="repsutApp.recipeToIngredient.home.createOrEditLabel" data-cy="RecipeToIngredientCreateUpdateHeading">
            <Translate contentKey="repsutApp.recipeToIngredient.home.createOrEditLabel">Create or edit a RecipeToIngredient</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="recipe-to-ingredient-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('repsutApp.recipeToIngredient.quantity')}
                id="recipe-to-ingredient-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('repsutApp.recipeToIngredient.unit')}
                id="recipe-to-ingredient-unit"
                name="unit"
                data-cy="unit"
                type="select"
              >
                {unitValues.map(unit => (
                  <option value={unit} key={unit}>
                    {translate(`repsutApp.Unit.${unit}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="recipe-to-ingredient-ingredient"
                name="ingredient"
                data-cy="ingredient"
                label={translate('repsutApp.recipeToIngredient.ingredient')}
                type="select"
              >
                <option value="" key="0" />
                {ingredients
                  ? ingredients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="recipe-to-ingredient-recipe"
                name="recipe"
                data-cy="recipe"
                label={translate('repsutApp.recipeToIngredient.recipe')}
                type="select"
              >
                <option value="" key="0" />
                {recipes
                  ? recipes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/recipe-to-ingredient" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RecipeToIngredientUpdate;
