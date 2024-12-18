package com.tuomas.repsut.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RecipeToIngredientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RecipeToIngredient getRecipeToIngredientSample1() {
        return new RecipeToIngredient().id(1L);
    }

    public static RecipeToIngredient getRecipeToIngredientSample2() {
        return new RecipeToIngredient().id(2L);
    }

    public static RecipeToIngredient getRecipeToIngredientRandomSampleGenerator() {
        return new RecipeToIngredient().id(longCount.incrementAndGet());
    }
}
