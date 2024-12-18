package com.tuomas.repsut.repository;

import com.tuomas.repsut.domain.RecipeToIngredient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RecipeToIngredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeToIngredientRepository extends JpaRepository<RecipeToIngredient, Long> {}
