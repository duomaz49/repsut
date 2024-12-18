import React from 'react';

import RecipeCard from '../RecipeCard';
import { useEffect, useState } from 'react';
import { Alert, Button, Container } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { clearMealDBRecipe, fetchByRecipeName } from 'app/client/reducers/recipeSliceMealDB';
import SearchBar from 'app/client/utils/SearchBar';

export default function SearchRecipe() {
  const dispatch = useAppDispatch();
  const [query, setQuery] = useState<string>('');
  const [showQueryAlert, setShowQueryAlert] = useState<boolean>(false);
  const { isLoading, error, recipe } = useAppSelector(state => state.recipeMealDB);

  useEffect(() => {
    dispatch(clearMealDBRecipe());
  }, [dispatch]);

  const getRecipeByName = () => {
    if (query === '') {
      setShowQueryAlert(true);
      return;
    }
    dispatch(fetchByRecipeName(query));
    setShowQueryAlert(false);
  };

  return (
    <Container fluid className="mt-5 p-5 d-flex flex-column justify-content-center align-items-center">
      <h6 className="text-center mb-4">Search for a recipe inspiration by recipe name</h6>
      <SearchBar query={query} setQuery={setQuery} placeholder={'Search for a recipe'} />
      <Button outline className="mb-4 custom-width" color="success" onClick={getRecipeByName}>
        Search
      </Button>
      {showQueryAlert && (
        <Alert color="warning" className="w-50">
          Please enter a recipe name!
        </Alert>
      )}
      {!isLoading && !error && recipe && Object.keys(recipe).length > 0 && (
        <div>
          <RecipeCard recipe={recipe} isNotSearch={false} />
        </div>
      )}
      {error && (
        <Alert color="danger" className="w-50">
          No recipe found with given name, please search again!
        </Alert>
      )}
    </Container>
  );
}
