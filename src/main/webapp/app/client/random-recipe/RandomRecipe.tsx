import '../clientcss.css';

import React from 'react';
import { useEffect } from 'react';
import RecipeCard from '../utils/RecipeCard';
import { Container } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { fetchRandomRecipe } from 'app/client/reducers/recipeSliceMealDB';
import SkeletonLoader from 'app/client/utils/SkeletonLoader';

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
    <Container fluid className="p-3 d-flex justify-content-center align-items-center">
      {isLoading && <SkeletonLoader length={25} />}
      {!isLoading && !error && (
        <div>
          <RecipeCard recipe={recipe} getRandomRecipe={getRandomRecipe} isNotSearch={true} />
        </div>
      )}
    </Container>
  );
}
