import '../clientcss.css';

import React, { useState } from 'react';
import { Button, Form, FormGroup, Input, Label } from 'reactstrap';
import { IRecipe } from 'app/shared/model/recipe.model';
import IngredientAccordion from 'app/client/crud-recipe/IngredientAccordion';
import dayjs from 'dayjs';

interface ICreateOrEditRecipeProps {
  existingRecipe?: IRecipe;
  saveRecipe: () => void;
}

export default function CreateOrEditRecipe(props: ICreateOrEditRecipeProps) {
  const { existingRecipe, saveRecipe } = props;
  const [ingredients, setIngredients] = useState<object[]>([]);
  const [recipe, setRecipe] = useState<IRecipe>({
    name: '',
    description: '',
    cookTime: 0,
    servings: 0,
    createdDate: dayjs(),
    instructions: '',
    ...existingRecipe,
  });

  return (
    <Form onSubmit={saveRecipe}>
      <FormGroup>
        <Label for="name" className="fw-bold ms-1">
          Name
        </Label>
        <Input required id="name" name="name" placeholder="Recipe name*" type="text" />
      </FormGroup>
      <FormGroup>
        <Label for="description" className="fw-bold ms-1">
          Description
        </Label>
        <Input id="description" name="description" placeholder="Description" type="text" />
      </FormGroup>
      <FormGroup>
        <Label for="cookTime" className="fw-bold ms-1">
          CookTime
        </Label>
        <Input id="cookTime" name="cookTime" placeholder="CookTime" type="number" />
      </FormGroup>
      <FormGroup>
        <Label for="servings" className="fw-bold ms-1">
          Servings
        </Label>
        <Input id="servings" name="servings" placeholder="Servings" type="number" />
      </FormGroup>
      <FormGroup>
        <Label for="instructions" className="fw-bold ms-1">
          Instructions
        </Label>
        <Input required id="instructions" name="instructions" placeholder="Instructions*" type="textarea" />
      </FormGroup>
      <IngredientAccordion create={true} ingredients={ingredients} setIngredients={setIngredients} />
      <Button outline color="success" block>
        Submit
      </Button>
    </Form>
  );
}
