package com.tuomas.repsut.web.rest;

import com.tuomas.repsut.domain.Recipe;
import com.tuomas.repsut.repository.RecipeRepository;
import com.tuomas.repsut.service.RecipeService;
import com.tuomas.repsut.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tuomas.repsut.domain.Recipe}.
 */
@RestController
@RequestMapping("/api/recipes")
@Transactional
public class RecipeResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeResource.class);

    private static final String ENTITY_NAME = "recipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeRepository recipeRepository;

    private final RecipeService recipeService;

    public RecipeResource(RecipeRepository recipeRepository, RecipeService recipeService) {
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    /**
     * {@code POST  /recipes} : Create a new recipe.
     *
     * @param recipe the recipe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipe, or with status {@code 400 (Bad Request)} if the recipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) throws URISyntaxException {
        LOG.debug("REST request to save Recipe : {}", recipe);
        if (recipe.getId() != null) {
            throw new BadRequestAlertException("A new recipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recipe = recipeService.saveOrUpdateRecipeWithIngredients(recipe);
        return ResponseEntity.created(new URI("/api/recipes/" + recipe.getName()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, recipe.getName()))
            .body(recipe);
    }

    /**
     * {@code PUT  /recipes/:id} : Updates an existing recipe.
     *
     * @param id the id of the recipe to save.
     * @param recipe the recipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipe,
     * or with status {@code 400 (Bad Request)} if the recipe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Recipe recipe
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recipe : {}, {}", id, recipe);
        if (recipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recipe = recipeService.saveOrUpdateRecipeWithIngredients(recipe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipe.getName()))
            .body(recipe);
    }

    /**
     * {@code PATCH  /recipes/:id} : Partial updates given fields of an existing recipe, field will ignore if it is null
     *
     * @param id the id of the recipe to save.
     * @param recipe the recipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipe,
     * or with status {@code 400 (Bad Request)} if the recipe is not valid,
     * or with status {@code 404 (Not Found)} if the recipe is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Recipe> partialUpdateRecipe(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Recipe recipe
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recipe partially : {}, {}", id, recipe);
        if (recipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Recipe> result = recipeRepository
            .findById(recipe.getId())
            .map(existingRecipe -> {
                if (recipe.getName() != null) {
                    existingRecipe.setName(recipe.getName());
                }
                if (recipe.getDescription() != null) {
                    existingRecipe.setDescription(recipe.getDescription());
                }
                if (recipe.getInstructions() != null) {
                    existingRecipe.setInstructions(recipe.getInstructions());
                }
                if (recipe.getCookTime() != null) {
                    existingRecipe.setCookTime(recipe.getCookTime());
                }
                if (recipe.getServings() != null) {
                    existingRecipe.setServings(recipe.getServings());
                }
                if (recipe.getCreatedDate() != null) {
                    existingRecipe.setCreatedDate(recipe.getCreatedDate());
                }

                return existingRecipe;
            })
            .map(recipeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipe.getName())
        );
    }

    /**
     * {@code GET  /recipes} : get all the recipes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipes in body.
     */
    @GetMapping("")
    public List<Recipe> getAllRecipes(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        LOG.debug("REST request to get all Recipes");
        if (eagerload) {
            return recipeRepository.findAllWithEagerRelationships();
        } else {
            return recipeRepository.findAll();
        }
    }

    /**
     * {@code GET  /recipes/:id} : get the "id" recipe.
     *
     * @param id the id of the recipe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recipe : {}", id);
        Optional<Recipe> recipe = recipeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(recipe);
    }

    /**
     * {@code DELETE  /recipes/:id} : delete the "id" recipe.
     *
     * @param id the id of the recipe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recipe : {}", id);
        Optional<Recipe> recipe = recipeRepository.findById(id);
        recipeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeletionAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    recipe.isPresent() ? recipe.get().getName() : id.toString()
                )
            )
            .build();
    }
}
