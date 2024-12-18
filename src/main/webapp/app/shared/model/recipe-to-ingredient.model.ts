import { IIngredient } from 'app/shared/model/ingredient.model';
import { IRecipe } from 'app/shared/model/recipe.model';
import { Unit } from 'app/shared/model/enumerations/unit.model';

export interface IRecipeToIngredient {
  id?: number;
  quantity?: number;
  unit?: keyof typeof Unit;
  ingredient?: IIngredient | null;
  recipe?: IRecipe | null;
}

export const defaultValue: Readonly<IRecipeToIngredient> = {};
