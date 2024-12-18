package com.tuomas.repsut.service;

import com.tuomas.repsut.domain.Ingredient;
import com.tuomas.repsut.domain.Recipe;
import com.tuomas.repsut.domain.RecipeToIngredient;
import com.tuomas.repsut.repository.IngredientRepository;
import com.tuomas.repsut.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Recipe saveOrUpdateRecipeWithIngredients(Recipe recipe) {
        for (RecipeToIngredient recipeToIngredient : recipe.getRecipeToIngredients()) {
            Ingredient ingredient = recipeToIngredient.getIngredient();
            Ingredient ingredientFromDb = ingredientRepository
                .findByName(ingredient.getName())
                .orElseGet(() -> ingredientRepository.save(ingredient));
            recipeToIngredient.setIngredient(ingredientFromDb);
            recipeToIngredient.setQuantity(recipeToIngredient.getQuantity());
            recipeToIngredient.setUnit(recipeToIngredient.getUnit());
            recipeToIngredient.setRecipe(recipe);
        }
        return recipeRepository.save(recipe);
    }
}
