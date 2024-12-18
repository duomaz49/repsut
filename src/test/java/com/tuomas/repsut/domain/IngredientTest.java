package com.tuomas.repsut.domain;

import static com.tuomas.repsut.domain.IngredientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tuomas.repsut.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IngredientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ingredient.class);
        Ingredient ingredient1 = getIngredientSample1();
        Ingredient ingredient2 = new Ingredient();
        assertThat(ingredient1).isNotEqualTo(ingredient2);

        ingredient2.setId(ingredient1.getId());
        assertThat(ingredient1).isEqualTo(ingredient2);

        ingredient2 = getIngredientSample2();
        assertThat(ingredient1).isNotEqualTo(ingredient2);
    }
}
