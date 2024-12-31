import React from 'react';
import { IRecipe } from 'app/shared/model/recipe.model';
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { Button, Col, Row } from 'reactstrap';
interface IRecipeCardProps {
  recipe: IRecipe;
}

export default function RecipeCard(props: IRecipeCardProps) {
  const { recipe } = props;
  return (
    <Row>
      <h6 className="text-center">Name: {recipe.name}</h6>
      <hr />
      <Col xs={12} md={6} className="mb-3">
        <div>
          Ingredients:
          {recipe.recipeToIngredients.map((obj, i) => (
            <div key={obj.id} className="d-flex gap-1">
              <span>{i + 1}.</span>
              <span>{obj.ingredient?.name}</span>
              <span>{obj.quantity}</span>
              <span>{obj.unit}</span>
            </div>
          ))}
        </div>
      </Col>
      <Col xs={12} md={6} className="mb-3">
        {recipe.description && <div>Description: {recipe.description}</div>}
        {recipe.servings && <div>Servings: {recipe.servings}</div>}
        {recipe.cookTime && <div>Cook time: {recipe.cookTime}</div>}
        <div>Created: {convertDateTimeFromServer(recipe.createdDate)}</div>
        <div>Categories: {recipe.categories?.map(category => category.name).join(', ')}</div>
      </Col>
      <hr />
      <div>Instructions: {recipe.instructions}</div>
    </Row>
  );
}
