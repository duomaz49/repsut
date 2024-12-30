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

export const API_URL: string = '/api/recipeMealDB';

export const fetchRandomRecipe = createAsyncThunk<IRecipeMealDB | null, void>('recipeMealDB/fetchRandomRecipe', async (_, { dispatch }) => {
  try {
    const options: AxiosRequestConfig = {
      method: 'GET',
      url: `${API_URL}/random-recipe`,
    };
    const response = await axios.request(options);
    // eslint-disable-next-line no-console
    console.log(response.data);
    return response.data;
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
        url: `${API_URL}/search-recipe-by-name`,
        params: { s: name },
      };
      const response = await axios.request(options);
      return response.data;
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
