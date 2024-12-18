package com.tuomas.repsut.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RecipeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Recipe getRecipeSample1() {
        return new Recipe().id(1L).name("name1").description("description1").cookTime(1).servings(1);
    }

    public static Recipe getRecipeSample2() {
        return new Recipe().id(2L).name("name2").description("description2").cookTime(2).servings(2);
    }

    public static Recipe getRecipeRandomSampleGenerator() {
        return new Recipe()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .cookTime(intCount.incrementAndGet())
            .servings(intCount.incrementAndGet());
    }
}
