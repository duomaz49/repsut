package com.tuomas.repsut.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MealsMealDBDTO {

    @JsonProperty("meals")
    private List<RecipeMealDBDTO> meals;

    public List<RecipeMealDBDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<RecipeMealDBDTO> meals) {
        this.meals = meals;
    }
}
