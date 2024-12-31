import React, { useEffect, useState } from 'react';
import '../clientcss.css';
import { Container, ListGroup, ListGroupItem } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/recipe/recipe.reducer';
import Overlay from 'app/client/utils/Overlay';
import RecipeCard from 'app/client/crud-recipe/RecipeCard';

export default function RecipesList() {
  const dispatch = useAppDispatch();
  const recipes = useAppSelector(state => state.recipe.entities);
  const [isRecipeModalOpen, setIsRecipeModalOpen] = useState<boolean>(false);
  const [selectedRecipeId, setSelectedRecipeId] = useState<number>(0);

  const toggleRecipeModal = () => setIsRecipeModalOpen(!isRecipeModalOpen);

  const openRecipeModal = (id: number) => {
    setSelectedRecipeId(id);
    toggleRecipeModal();
  };

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  return (
    <Container fluid className="d-flex flex-column justify-content-center align-items-center">
      <h1>Your recipes!</h1>
      <ListGroup className="custom-width">
        {recipes.map((recipe, i) => (
          <ListGroupItem
            key={i}
            className="recipe-item bg-transparent border-3 border-success rounded-2 mb-2 shadow-sm text-center"
            onClick={() => openRecipeModal(recipe.id)}
          >
            {recipe.name}
          </ListGroupItem>
        ))}
      </ListGroup>
      <Overlay isOpen={isRecipeModalOpen} toggle={toggleRecipeModal} title="Recipe Details">
        <RecipeCard recipe={recipes.find(recipe => recipe.id === selectedRecipeId)} />
      </Overlay>
    </Container>
  );
}
