import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';
import { IRecipeToIngredient } from 'app/shared/model/recipe-to-ingredient.model';

export interface IRecipe {
  id?: number;
  name?: string;
  description?: string | null;
  instructions?: string;
  cookTime?: number | null;
  servings?: number;
  createdDate?: dayjs.Dayjs;
  categories?: ICategory[] | null;
  recipeToIngredients?: IRecipeToIngredient[] | null;
}

export const defaultValue: Readonly<IRecipe> = {};
