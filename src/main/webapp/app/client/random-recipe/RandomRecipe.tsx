import '../clientcss.css';

import React from 'react';
import { useEffect } from 'react';
import RecipeCard from '../utils/RecipeCard';
import { Container } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { fetchRandomRecipe } from 'app/client/reducers/recipeSliceMealDB';

export default function RandomRecipe() {
  const dispatch = useAppDispatch();
  const { isLoading, error, recipe } = useAppSelector(state => state.recipeMealDB);

  useEffect(() => {
    getRandomRecipe();
  }, [dispatch]);

  const getRandomRecipe = () => {
    dispatch(fetchRandomRecipe());
  };

  return (
    <Container fluid className="mt-5 p-5 d-flex flex-column justify-content-center align-items-center">
      {!isLoading && !error && (
        <div>
          <RecipeCard recipe={recipe} getRandomRecipe={getRandomRecipe} isNotSearch={true} />
        </div>
      )}
    </Container>
  );
}
