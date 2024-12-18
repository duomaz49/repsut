package com.tuomas.repsut.domain;

import static com.tuomas.repsut.domain.CategoryTestSamples.*;
import static com.tuomas.repsut.domain.RecipeTestSamples.*;
import static com.tuomas.repsut.domain.RecipeToIngredientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tuomas.repsut.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RecipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipe.class);
        Recipe recipe1 = getRecipeSample1();
        Recipe recipe2 = new Recipe();
        assertThat(recipe1).isNotEqualTo(recipe2);

        recipe2.setId(recipe1.getId());
        assertThat(recipe1).isEqualTo(recipe2);

        recipe2 = getRecipeSample2();
        assertThat(recipe1).isNotEqualTo(recipe2);
    }

    @Test
    void recipeToIngredientTest() {
        Recipe recipe = getRecipeRandomSampleGenerator();
        RecipeToIngredient recipeToIngredientBack = getRecipeToIngredientRandomSampleGenerator();

        recipe.addRecipeToIngredient(recipeToIngredientBack);
        assertThat(recipe.getRecipeToIngredients()).containsOnly(recipeToIngredientBack);
        assertThat(recipeToIngredientBack.getRecipe()).isEqualTo(recipe);

        recipe.removeRecipeToIngredient(recipeToIngredientBack);
        assertThat(recipe.getRecipeToIngredients()).doesNotContain(recipeToIngredientBack);
        assertThat(recipeToIngredientBack.getRecipe()).isNull();

        recipe.recipeToIngredients(new HashSet<>(Set.of(recipeToIngredientBack)));
        assertThat(recipe.getRecipeToIngredients()).containsOnly(recipeToIngredientBack);
        assertThat(recipeToIngredientBack.getRecipe()).isEqualTo(recipe);

        recipe.setRecipeToIngredients(new HashSet<>());
        assertThat(recipe.getRecipeToIngredients()).doesNotContain(recipeToIngredientBack);
        assertThat(recipeToIngredientBack.getRecipe()).isNull();
    }

    @Test
    void categoryTest() {
        Recipe recipe = getRecipeRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        recipe.addCategory(categoryBack);
        assertThat(recipe.getCategories()).containsOnly(categoryBack);

        recipe.removeCategory(categoryBack);
        assertThat(recipe.getCategories()).doesNotContain(categoryBack);

        recipe.categories(new HashSet<>(Set.of(categoryBack)));
        assertThat(recipe.getCategories()).containsOnly(categoryBack);

        recipe.setCategories(new HashSet<>());
        assertThat(recipe.getCategories()).doesNotContain(categoryBack);
    }
}
