import React from 'react';
import { IRecipe } from 'app/shared/model/recipe.model';
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';

interface IRecipeCardProps {
  recipe: IRecipe;
}

export default function RecipeCard(props: IRecipeCardProps) {
  const { recipe } = props;
  return (
    <div>
      <h1>Name: {recipe.name}</h1>
      <p>Servings: {recipe.servings}</p>
      <p>Cook time: {recipe.cookTime}</p>
      <p>Created: {convertDateTimeFromServer(recipe.createdDate)}</p>
      <p>Categories: {recipe.categories?.map(category => category.name).join(', ')}</p>
      <p>Description: {recipe.description}</p>
      <p>Instructions: {recipe.instructions}</p>
      <div>
        {recipe.recipeToIngredients.map(obj => (
          <div key={obj.id}>
            <p>Ingredient: {obj.ingredient?.name}</p>
            <p>Quantity: {obj.quantity}</p>
            <p>Unit: {obj.unit}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
