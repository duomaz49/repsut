import React from 'react';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Category from './category';
import Ingredient from './ingredient';
import Recipe from './recipe';
import RecipeToIngredient from './recipe-to-ingredient';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="category/*" element={<Category />} />
        <Route path="ingredient/*" element={<Ingredient />} />
        <Route path="recipe/*" element={<Recipe />} />
        <Route path="recipe-to-ingredient/*" element={<RecipeToIngredient />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
