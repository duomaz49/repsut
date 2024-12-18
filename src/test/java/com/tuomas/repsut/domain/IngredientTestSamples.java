package com.tuomas.repsut.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IngredientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ingredient getIngredientSample1() {
        return new Ingredient().id(1L).name("name1");
    }

    public static Ingredient getIngredientSample2() {
        return new Ingredient().id(2L).name("name2");
    }

    public static Ingredient getIngredientRandomSampleGenerator() {
        return new Ingredient().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
