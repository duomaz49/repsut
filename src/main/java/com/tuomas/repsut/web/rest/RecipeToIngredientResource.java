package com.tuomas.repsut.web.rest;

import com.tuomas.repsut.domain.RecipeToIngredient;
import com.tuomas.repsut.repository.RecipeToIngredientRepository;
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
 * REST controller for managing {@link com.tuomas.repsut.domain.RecipeToIngredient}.
 */
@RestController
@RequestMapping("/api/recipe-to-ingredients")
@Transactional
public class RecipeToIngredientResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeToIngredientResource.class);

    private static final String ENTITY_NAME = "recipeToIngredient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeToIngredientRepository recipeToIngredientRepository;

    public RecipeToIngredientResource(RecipeToIngredientRepository recipeToIngredientRepository) {
        this.recipeToIngredientRepository = recipeToIngredientRepository;
    }

    /**
     * {@code POST  /recipe-to-ingredients} : Create a new recipeToIngredient.
     *
     * @param recipeToIngredient the recipeToIngredient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeToIngredient, or with status {@code 400 (Bad Request)} if the recipeToIngredient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecipeToIngredient> createRecipeToIngredient(@Valid @RequestBody RecipeToIngredient recipeToIngredient)
        throws URISyntaxException {
        LOG.debug("REST request to save RecipeToIngredient : {}", recipeToIngredient);
        if (recipeToIngredient.getId() != null) {
            throw new BadRequestAlertException("A new recipeToIngredient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recipeToIngredient = recipeToIngredientRepository.save(recipeToIngredient);
        return ResponseEntity.created(new URI("/api/recipe-to-ingredients/" + recipeToIngredient.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, recipeToIngredient.getId().toString()))
            .body(recipeToIngredient);
    }

    /**
     * {@code PUT  /recipe-to-ingredients/:id} : Updates an existing recipeToIngredient.
     *
     * @param id the id of the recipeToIngredient to save.
     * @param recipeToIngredient the recipeToIngredient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeToIngredient,
     * or with status {@code 400 (Bad Request)} if the recipeToIngredient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeToIngredient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeToIngredient> updateRecipeToIngredient(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecipeToIngredient recipeToIngredient
    ) throws URISyntaxException {
        LOG.debug("REST request to update RecipeToIngredient : {}, {}", id, recipeToIngredient);
        if (recipeToIngredient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeToIngredient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeToIngredientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recipeToIngredient = recipeToIngredientRepository.save(recipeToIngredient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeToIngredient.getId().toString()))
            .body(recipeToIngredient);
    }

    /**
     * {@code PATCH  /recipe-to-ingredients/:id} : Partial updates given fields of an existing recipeToIngredient, field will ignore if it is null
     *
     * @param id the id of the recipeToIngredient to save.
     * @param recipeToIngredient the recipeToIngredient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeToIngredient,
     * or with status {@code 400 (Bad Request)} if the recipeToIngredient is not valid,
     * or with status {@code 404 (Not Found)} if the recipeToIngredient is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipeToIngredient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecipeToIngredient> partialUpdateRecipeToIngredient(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecipeToIngredient recipeToIngredient
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RecipeToIngredient partially : {}, {}", id, recipeToIngredient);
        if (recipeToIngredient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeToIngredient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeToIngredientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecipeToIngredient> result = recipeToIngredientRepository
            .findById(recipeToIngredient.getId())
            .map(existingRecipeToIngredient -> {
                if (recipeToIngredient.getQuantity() != null) {
                    existingRecipeToIngredient.setQuantity(recipeToIngredient.getQuantity());
                }
                if (recipeToIngredient.getUnit() != null) {
                    existingRecipeToIngredient.setUnit(recipeToIngredient.getUnit());
                }

                return existingRecipeToIngredient;
            })
            .map(recipeToIngredientRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeToIngredient.getId().toString())
        );
    }

    /**
     * {@code GET  /recipe-to-ingredients} : get all the recipeToIngredients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeToIngredients in body.
     */
    @GetMapping("")
    public List<RecipeToIngredient> getAllRecipeToIngredients() {
        LOG.debug("REST request to get all RecipeToIngredients");
        return recipeToIngredientRepository.findAll();
    }

    /**
     * {@code GET  /recipe-to-ingredients/:id} : get the "id" recipeToIngredient.
     *
     * @param id the id of the recipeToIngredient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeToIngredient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeToIngredient> getRecipeToIngredient(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RecipeToIngredient : {}", id);
        Optional<RecipeToIngredient> recipeToIngredient = recipeToIngredientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recipeToIngredient);
    }

    /**
     * {@code DELETE  /recipe-to-ingredients/:id} : delete the "id" recipeToIngredient.
     *
     * @param id the id of the recipeToIngredient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeToIngredient(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RecipeToIngredient : {}", id);
        recipeToIngredientRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
