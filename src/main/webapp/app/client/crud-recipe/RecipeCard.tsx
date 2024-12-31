import React from 'react';
import { IRecipe } from 'app/shared/model/recipe.model';
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { Button, Card, CardBody, CardTitle, Col, ListGroup, ListGroupItem, Row } from 'reactstrap';
interface IRecipeCardProps {
  recipe: IRecipe;
}

export default function RecipeCard(props: IRecipeCardProps) {
  const { recipe } = props;
  return (
    <div>
      <h6>Name: {recipe.name}</h6>
      <div>Description: {recipe.description}</div>
      <div>Servings: {recipe.servings}</div>
      <div>Cook time: {recipe.cookTime}</div>
      <div>Created: {convertDateTimeFromServer(recipe.createdDate)}</div>
      <div>Categories: {recipe.categories?.map(category => category.name).join(', ')}</div>
      <div>
        {recipe.recipeToIngredients.map(obj => (
          <div key={obj.id}>
            <div>Ingredient: {obj.ingredient?.name}</div>
            <div>Quantity: {obj.quantity}</div>
            <div>Unit: {obj.unit}</div>
          </div>
        ))}
      </div>
      <div>Instructions: {recipe.instructions}</div>
    </div>
  );
}
