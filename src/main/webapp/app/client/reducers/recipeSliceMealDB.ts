import { createAsyncThunk, createSlice, isFulfilled, isPending } from '@reduxjs/toolkit';
import axios, { AxiosRequestConfig } from 'axios';
import { IRecipeMealDB } from './recipe-type';

interface RecipeState {
  recipe: IRecipeMealDB | null;
  error: boolean;
  isLoading: boolean;
}

const initialState: RecipeState = {
  recipe: null,
  error: false,
  isLoading: false,
};

export const API_URL: string = 'https://cors-anywhere.herokuapp.com/https://www.themealdb.com/api/json/v1/1';

export const fetchRandomRecipe = createAsyncThunk<IRecipeMealDB | null, void>('recipeMealDB/fetchRandomRecipe', async (_, { dispatch }) => {
  try {
    const options: AxiosRequestConfig = {
      method: 'GET',
      url: `${API_URL}/random.php`,
    };
    const response = await axios.request(options);
    if (response.data.meals && response.data.meals.length > 0) {
      return response.data.meals[0];
    } else {
      dispatch(setRecipeError('No recipe found.'));
      return null;
    }
  } catch (error) {
    console.error('Error fetching recipes:', error);
    dispatch(setRecipeError('Failed to fetch recipes.'));
    return null;
  }
});

export const fetchByRecipeName = createAsyncThunk<IRecipeMealDB | null, string>(
  'recipeMealDB/fetchByRecipeName',
  async (name: string, { dispatch }) => {
    try {
      const options: { method: string; params: { s: string }; url: string } = {
        method: 'GET',
        url: `${API_URL}/search.php`,
        params: { s: name },
      };
      const response = await axios.request(options);
      if (response.data.meals && response.data.meals.length > 0) {
        return response.data.meals[0];
      } else {
        dispatch(setRecipeError('No recipe found.'));
        return null;
      }
    } catch (error) {
      console.error('Error fetching recipes:', error);
      dispatch(setRecipeError('Failed to fetch recipes.'));
      return null;
    }
  },
);

const recipeSliceMealDB = createSlice({
  name: 'recipeMealDB',
  initialState,
  reducers: {
    clearMealDBRecipe(state) {
      return initialState;
    },
    setRecipeError(state, action) {
      state.isLoading = false;
      state.error = true;
      state.recipe = null;
    },
  },
  extraReducers(builder) {
    builder
      .addMatcher(isPending(fetchRandomRecipe, fetchByRecipeName), state => {
        state.isLoading = true;
        state.error = false;
        state.recipe = null;
      })
      .addMatcher(isFulfilled(fetchRandomRecipe, fetchByRecipeName), (state, action) => {
        state.isLoading = false;
        if (action.payload === null) {
          state.error = true;
          state.recipe = null;
        } else {
          state.error = false;
          state.recipe = action.payload;
        }
      });
  },
});

export const { clearMealDBRecipe, setRecipeError } = recipeSliceMealDB.actions;
export default recipeSliceMealDB.reducer;
