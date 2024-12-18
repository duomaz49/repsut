package com.tuomas.repsut.domain;

import static com.tuomas.repsut.domain.IngredientTestSamples.*;
import static com.tuomas.repsut.domain.RecipeTestSamples.*;
import static com.tuomas.repsut.domain.RecipeToIngredientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tuomas.repsut.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecipeToIngredientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeToIngredient.class);
        RecipeToIngredient recipeToIngredient1 = getRecipeToIngredientSample1();
        RecipeToIngredient recipeToIngredient2 = new RecipeToIngredient();
        assertThat(recipeToIngredient1).isNotEqualTo(recipeToIngredient2);

        recipeToIngredient2.setId(recipeToIngredient1.getId());
        assertThat(recipeToIngredient1).isEqualTo(recipeToIngredient2);

        recipeToIngredient2 = getRecipeToIngredientSample2();
        assertThat(recipeToIngredient1).isNotEqualTo(recipeToIngredient2);
    }

    @Test
    void ingredientTest() {
        RecipeToIngredient recipeToIngredient = getRecipeToIngredientRandomSampleGenerator();
        Ingredient ingredientBack = getIngredientRandomSampleGenerator();

        recipeToIngredient.setIngredient(ingredientBack);
        assertThat(recipeToIngredient.getIngredient()).isEqualTo(ingredientBack);

        recipeToIngredient.ingredient(null);
        assertThat(recipeToIngredient.getIngredient()).isNull();
    }

    @Test
    void recipeTest() {
        RecipeToIngredient recipeToIngredient = getRecipeToIngredientRandomSampleGenerator();
        Recipe recipeBack = getRecipeRandomSampleGenerator();

        recipeToIngredient.setRecipe(recipeBack);
        assertThat(recipeToIngredient.getRecipe()).isEqualTo(recipeBack);

        recipeToIngredient.recipe(null);
        assertThat(recipeToIngredient.getRecipe()).isNull();
    }
}
