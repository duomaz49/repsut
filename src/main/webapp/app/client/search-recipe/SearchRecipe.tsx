import '../clientcss.css';

import React from 'react';

import MealDBRecipeCard from '../utils/MealDBRecipeCard';
import { useEffect, useState } from 'react';
import { Alert, Button, Container } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { clearMealDBRecipe, fetchByRecipeName } from 'app/client/reducers/recipeSliceMealDB';
import SearchBar from 'app/client/utils/SearchBar';
import SkeletonLoader from 'app/client/utils/SkeletonLoader';
import { translate, Translate } from 'react-jhipster';

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
    <Container fluid className="p-3 d-flex flex-column justify-content-center align-items-center">
      <h6 className="text-center mb-4">
        <Translate contentKey="searchRecipe.search">Search for a recipe inspiration by recipe name</Translate>
      </h6>
      <SearchBar query={query} setQuery={setQuery} placeholder={translate('searchRecipe.placeholder')} onSearch={getRecipeByName} />
      <Button outline className="mb-4 custom-width" color="success" onClick={getRecipeByName}>
        <Translate contentKey="searchRecipe.searchButton">Search</Translate>
      </Button>
      {showQueryAlert && (
        <Alert color="warning" className="custom-width text-center">
          <Translate contentKey="searchRecipe.warning">Please enter a recipe name!</Translate>
        </Alert>
      )}
      {isLoading && <SkeletonLoader length={25} />}
      {!isLoading && !error && recipe && Object.keys(recipe).length > 0 && (
        <div>
          <MealDBRecipeCard recipe={recipe} isNotSearch={false} />
        </div>
      )}
      {error && recipe === null && (
        <Alert color="danger" className="custom-width text-center">
          <Translate contentKey="searchRecipe.noResults">No recipe found with given name, please search again!</Translate>
        </Alert>
      )}
    </Container>
  );
}
