package com.tuomas.repsut.repository;

import com.tuomas.repsut.domain.Recipe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RecipeRepositoryWithBagRelationshipsImpl implements RecipeRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String RECIPES_PARAMETER = "recipes";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Recipe> fetchBagRelationships(Optional<Recipe> recipe) {
        return recipe.map(this::fetchCategories);
    }

    @Override
    public Page<Recipe> fetchBagRelationships(Page<Recipe> recipes) {
        return new PageImpl<>(fetchBagRelationships(recipes.getContent()), recipes.getPageable(), recipes.getTotalElements());
    }

    @Override
    public List<Recipe> fetchBagRelationships(List<Recipe> recipes) {
        return Optional.of(recipes).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    Recipe fetchCategories(Recipe result) {
        return entityManager
            .createQuery("select recipe from Recipe recipe left join fetch recipe.categories where recipe.id = :id", Recipe.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Recipe> fetchCategories(List<Recipe> recipes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, recipes.size()).forEach(index -> order.put(recipes.get(index).getId(), index));
        List<Recipe> result = entityManager
            .createQuery("select recipe from Recipe recipe left join fetch recipe.categories where recipe in :recipes", Recipe.class)
            .setParameter(RECIPES_PARAMETER, recipes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
