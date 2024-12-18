import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RecipeToIngredient from './recipe-to-ingredient';
import RecipeToIngredientDetail from './recipe-to-ingredient-detail';
import RecipeToIngredientUpdate from './recipe-to-ingredient-update';
import RecipeToIngredientDeleteDialog from './recipe-to-ingredient-delete-dialog';

const RecipeToIngredientRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RecipeToIngredient />} />
    <Route path="new" element={<RecipeToIngredientUpdate />} />
    <Route path=":id">
      <Route index element={<RecipeToIngredientDetail />} />
      <Route path="edit" element={<RecipeToIngredientUpdate />} />
      <Route path="delete" element={<RecipeToIngredientDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RecipeToIngredientRoutes;
