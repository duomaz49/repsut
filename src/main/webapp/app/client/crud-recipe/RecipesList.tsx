import React, { useEffect, useState } from 'react';
import '../clientcss.css';
import { Container, ListGroup, ListGroupItem, Button } from 'reactstrap';
import { FaPlus } from 'react-icons/fa';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/recipe/recipe.reducer';
import Overlay from 'app/client/utils/Overlay';
import RecipeCard from 'app/client/crud-recipe/RecipeCard';
import SearchBar from 'app/client/utils/SearchBar';
import CreateOrEditRecipe from 'app/client/crud-recipe/CreateOrEditRecipe';

export default function RecipesList() {
  const dispatch = useAppDispatch();
  const recipes = useAppSelector(state => state.recipe.entities);
  const [isRecipeModalOpen, setIsRecipeModalOpen] = useState<boolean>(false);
  const [selectedRecipeId, setSelectedRecipeId] = useState<number>(0);
  const [isCreateOrEditModalOpen, setIsCreateOrEditModalOpen] = useState<boolean>(false);
  const [query, setQuery] = useState<string>('');

  const toggleRecipeDetailModal = () => setIsRecipeModalOpen(!isRecipeModalOpen);

  const toggleCreateOrEditModal = () => setIsCreateOrEditModalOpen(!isCreateOrEditModalOpen);

  const openRecipeDetailModal = (id: number) => {
    setSelectedRecipeId(id);
    toggleRecipeDetailModal();
  };

  const openCreateOrEditModal = (id: number) => {
    setSelectedRecipeId(id);
    if (id) {
      toggleRecipeDetailModal();
    }
    toggleCreateOrEditModal();
  };

  const filteredRecipes = recipes.filter(recipe => {
    return recipe.name.toLowerCase().includes(query.toLowerCase()) || recipe.description.toLowerCase().includes(query.toLowerCase());
  });

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  return (
    <Container fluid className="d-flex flex-column justify-content-center align-items-center">
      <h1>Your recipes!</h1>
      <SearchBar query={query} setQuery={setQuery} placeholder={'Search for a recipe'} />
      <Button
        color="success"
        className="d-flex justify-content-center align-items-center custom-width mb-3"
        onClick={() => openCreateOrEditModal(null)}
      >
        <FaPlus color="white" className="me-1" /> Add Recipe
      </Button>
      <ListGroup className="custom-width">
        {filteredRecipes.map((recipe, i) => (
          <ListGroupItem
            key={i}
            className="recipe-item bg-transparent border-3 border-success rounded-2 mb-2 shadow-sm text-center"
            onClick={() => openRecipeDetailModal(recipe.id)}
          >
            {recipe?.name} - {recipe?.description}
          </ListGroupItem>
        ))}
      </ListGroup>
      <Overlay isOpen={isRecipeModalOpen} toggle={toggleRecipeDetailModal} title="Recipe Details">
        <RecipeCard
          recipe={recipes.find(recipe => recipe.id === selectedRecipeId)}
          toggleRecipeDetailModal={toggleRecipeDetailModal}
          openCreateOrEditModal={openCreateOrEditModal}
        />
      </Overlay>
      <Overlay isOpen={isCreateOrEditModalOpen} toggle={toggleCreateOrEditModal} title="Create or Edit Recipe">
        <CreateOrEditRecipe
          existingRecipe={recipes.find(recipe => recipe.id === selectedRecipeId)}
          toggleCreateOrEditModal={toggleCreateOrEditModal}
        />
      </Overlay>
    </Container>
  );
}
