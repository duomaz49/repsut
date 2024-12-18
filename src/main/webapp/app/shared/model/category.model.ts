import { IRecipe } from 'app/shared/model/recipe.model';

export interface ICategory {
  id?: number;
  name?: string;
  recipes?: IRecipe[] | null;
}

export const defaultValue: Readonly<ICategory> = {};
