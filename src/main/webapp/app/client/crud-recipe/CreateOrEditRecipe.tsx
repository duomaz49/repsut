import React from 'react';
import { Container } from 'reactstrap';

interface CreateOrEditRecipeProps {}
export default function CreateOrEditRecipe(props: CreateOrEditRecipeProps) {
  return (
    <Container fluid className="vh-100 d-flex flex-column justify-content-center align-items-center">
      <div className="m-auto text-center">
        <h1>Create or Edit Recipe</h1>
      </div>
    </Container>
  );
}
