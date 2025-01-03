import '../clientcss.css';

import React, { useEffect, useState } from 'react';
import { Button, Form, FormGroup, Input, Label } from 'reactstrap';
import { IRecipe } from 'app/shared/model/recipe.model';
import IngredientAccordion from 'app/client/crud-recipe/IngredientAccordion';
import dayjs from 'dayjs';
import { createEntity, updateEntity } from 'app/entities/recipe/recipe.reducer';
import { useAppDispatch } from 'app/config/store';
import { IRecipeToIngredient } from 'app/shared/model/recipe-to-ingredient.model';

interface ICreateOrEditRecipeProps {
  existingRecipe?: IRecipe;
  toggleCreateOrEditModal: () => void;
}

export default function CreateOrEditRecipe(props: ICreateOrEditRecipeProps) {
  const dispatch = useAppDispatch();

  const { existingRecipe, toggleCreateOrEditModal } = props;
  const [ingredients, setIngredients] = useState<any[]>([]);
  const [recipe, setRecipe] = useState<IRecipe>({
    name: '',
    description: '',
    cookTime: 0,
    servings: 0,
    createdDate: dayjs(),
    instructions: '',
    ...existingRecipe,
  });

  useEffect(() => {
    if (existingRecipe) {
      setRecipe(existingRecipe);
      const combinedIngredients = existingRecipe.recipeToIngredients?.map((recipeToIngredient: IRecipeToIngredient) => ({
        ...recipeToIngredient,
        id: recipeToIngredient.ingredient?.id,
        name: recipeToIngredient.ingredient?.name,
        unit: recipeToIngredient.unit,
        quantity: recipeToIngredient.quantity,
      }));
      setIngredients(combinedIngredients || []);
    }
  }, [existingRecipe]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setRecipe({ ...recipe, [name]: value });
  };

  const saveRecipe = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const recipeEntity = {
      ...recipe,
      recipeToIngredients: [
        ...ingredients.map(ingredient => ({
          unit: ingredient.unit,
          quantity: ingredient.quantity,
          ingredient: { name: ingredient.name },
        })),
      ],
    };

    if (existingRecipe) {
      dispatch(updateEntity(recipeEntity));
      toggleCreateOrEditModal();
    } else {
      dispatch(createEntity(recipeEntity));
      toggleCreateOrEditModal();
    }
  };

  return (
    <Form onSubmit={saveRecipe}>
      <FormGroup>
        <Label for="name" className="fw-bold ms-1">
          Name
        </Label>
        <Input required id="name" name="name" value={recipe.name} onChange={handleChange} placeholder="Recipe name*" type="text" />
      </FormGroup>
      <FormGroup>
        <Label for="description" className="fw-bold ms-1">
          Description
        </Label>
        <Input
          id="description"
          name="description"
          value={recipe.description}
          onChange={handleChange}
          placeholder="Description"
          type="text"
        />
      </FormGroup>
      <FormGroup>
        <Label for="cookTime" className="fw-bold ms-1">
          CookTime
        </Label>
        <Input id="cookTime" name="cookTime" value={recipe.cookTime} onChange={handleChange} placeholder="CookTime" type="number" />
      </FormGroup>
      <FormGroup>
        <Label for="servings" className="fw-bold ms-1">
          Servings
        </Label>
        <Input id="servings" name="servings" value={recipe.servings} onChange={handleChange} placeholder="Servings" type="number" />
      </FormGroup>
      <FormGroup>
        <Label for="instructions" className="fw-bold ms-1">
          Instructions
        </Label>
        <Input
          required
          id="instructions"
          name="instructions"
          value={recipe.instructions}
          onChange={handleChange}
          placeholder="Instructions*"
          type="textarea"
        />
      </FormGroup>
      <IngredientAccordion ingredients={ingredients} setIngredients={setIngredients} />
      <Button outline color="success" block>
        Submit
      </Button>
    </Form>
  );
}
