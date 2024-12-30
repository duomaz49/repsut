package com.tuomas.repsut.web.rest;

import com.tuomas.repsut.service.dto.MealsMealDBDTO;
import com.tuomas.repsut.service.dto.RecipeMealDBDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/recipeMealDB")
public class MealDBResource {

    private final RestTemplate restTemplate;

    private final String mealDbApiUrl = "https://www.themealdb.com/api/json/v1/1";

    public MealDBResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/random-recipe")
    public ResponseEntity<Object> getRandomRecipe() {
        String url = mealDbApiUrl + "/random.php";
        try {
            MealsMealDBDTO response = restTemplate.getForObject(url, MealsMealDBDTO.class);

            if (response != null && response.getMeals() != null && !response.getMeals().isEmpty()) {
                RecipeMealDBDTO meal = response.getMeals().get(0);
                return ResponseEntity.ok(meal);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Random recipe not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching data from MealDB");
        }
    }

    @GetMapping("/search-recipe-by-name")
    public ResponseEntity<Object> getRecipeByName(@RequestParam String s) {
        String url = mealDbApiUrl + "/search.php?s=" + s;
        try {
            MealsMealDBDTO response = restTemplate.getForObject(url, MealsMealDBDTO.class);

            if (response != null && response.getMeals() != null && !response.getMeals().isEmpty()) {
                RecipeMealDBDTO meal = response.getMeals().get(0);
                return ResponseEntity.ok(meal);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching data from MealDB");
        }
    }
}
