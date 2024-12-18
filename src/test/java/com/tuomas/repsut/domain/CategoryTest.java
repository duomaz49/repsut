package com.tuomas.repsut.domain;

import static com.tuomas.repsut.domain.CategoryTestSamples.*;
import static com.tuomas.repsut.domain.RecipeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tuomas.repsut.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void recipeTest() {
        Category category = getCategoryRandomSampleGenerator();
        Recipe recipeBack = getRecipeRandomSampleGenerator();

        category.addRecipe(recipeBack);
        assertThat(category.getRecipes()).containsOnly(recipeBack);
        assertThat(recipeBack.getCategories()).containsOnly(category);

        category.removeRecipe(recipeBack);
        assertThat(category.getRecipes()).doesNotContain(recipeBack);
        assertThat(recipeBack.getCategories()).doesNotContain(category);

        category.recipes(new HashSet<>(Set.of(recipeBack)));
        assertThat(category.getRecipes()).containsOnly(recipeBack);
        assertThat(recipeBack.getCategories()).containsOnly(category);

        category.setRecipes(new HashSet<>());
        assertThat(category.getRecipes()).doesNotContain(recipeBack);
        assertThat(recipeBack.getCategories()).doesNotContain(category);
    }
}
