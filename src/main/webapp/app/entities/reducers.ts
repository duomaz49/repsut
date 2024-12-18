import category from 'app/entities/category/category.reducer';
import ingredient from 'app/entities/ingredient/ingredient.reducer';
import recipe from 'app/entities/recipe/recipe.reducer';
import recipeToIngredient from 'app/entities/recipe-to-ingredient/recipe-to-ingredient.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  category,
  ingredient,
  recipe,
  recipeToIngredient,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
