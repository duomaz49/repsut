import React, { useEffect } from 'react';
import '../clientcss.css';
import { Container, ListGroup, ListGroupItem } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/recipe/recipe.reducer';

export default function RecipesList() {
  const dispatch = useAppDispatch();
  const recipes = useAppSelector(state => state.recipe.entities);
  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  return (
    <Container fluid className="d-flex flex-column justify-content-center align-items-center">
      <h1>Recipes List</h1>
      <ListGroup className="custom-width">
        {recipes.map((recipe, i) => (
          <ListGroupItem key={i} className="recipe-item bg-transparent border-3 border-success rounded-2 mb-2 shadow-sm text-center">
            {' '}
            {recipe.name}
          </ListGroupItem>
        ))}
      </ListGroup>
    </Container>
  );
}
