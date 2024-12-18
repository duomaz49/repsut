import { createAsyncThunk, createSlice, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import axios from 'axios';
import { IRecipeMealDB } from './recipe-type';

interface RecipeState {
  recipe: IRecipeMealDB;
  error: boolean;
  isLoading: boolean;
}

const initialState: RecipeState = {
  recipe: {} as IRecipeMealDB,
  error: false,
  isLoading: false,
};

export const API_URL: string = 'https://www.themealdb.com/api/json/v1/1';

export const fetchRandomRecipe = createAsyncThunk<IRecipeMealDB>('recipeMealDB/fetchRandomRecipe', async (_, { rejectWithValue }) => {
  try {
    const options: { method: string; url: string } = {
      method: 'GET',
      url: `${API_URL}/random.php`,
    };
    const response = await axios.request(options);
    if (response.data.meals && response.data.meals.length > 0) {
      return response.data.meals[0];
    } else {
      return rejectWithValue('No recipe found.');
    }
  } catch (error) {
    console.error('Error fetching recipes:', error);
    return rejectWithValue('Failed to fetch recipes.');
  }
});

export const fetchByRecipeName = createAsyncThunk<IRecipeMealDB, string>(
  'recipeMealDB/fetchByRecipeName',
  async (name: string, { rejectWithValue }) => {
    try {
      const options: { method: string; params: { s: string }; url: string } = {
        method: 'GET',
        url: `${API_URL}/search.php`,
        params: {
          s: name,
        },
      };
      const response = await axios.request(options);
      if (response.data.meals && response.data.meals.length > 0) {
        return response.data.meals[0];
      } else {
        return rejectWithValue('No recipe found.');
      }
    } catch (error) {
      console.error('Error fetching recipes:', error);
      return rejectWithValue('Failed to fetch recipes.');
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
  },
  extraReducers(builder) {
    builder
      .addMatcher(isPending(fetchRandomRecipe, fetchByRecipeName), state => {
        state.isLoading = true;
        state.error = false;
      })
      .addMatcher(isFulfilled(fetchRandomRecipe, fetchByRecipeName), (state, action) => {
        state.isLoading = false;
        state.error = false;
        state.recipe = action.payload;
      })
      .addMatcher(isRejected(fetchRandomRecipe, fetchByRecipeName), state => {
        state.isLoading = false;
        state.error = true;
      });
  },
});

export const { clearMealDBRecipe } = recipeSliceMealDB.actions;
export default recipeSliceMealDB.reducer;
