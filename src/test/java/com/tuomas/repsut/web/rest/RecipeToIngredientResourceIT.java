package com.tuomas.repsut.web.rest;

import static com.tuomas.repsut.domain.RecipeToIngredientAsserts.*;
import static com.tuomas.repsut.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuomas.repsut.IntegrationTest;
import com.tuomas.repsut.domain.RecipeToIngredient;
import com.tuomas.repsut.domain.enumeration.Unit;
import com.tuomas.repsut.repository.RecipeToIngredientRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RecipeToIngredientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecipeToIngredientResourceIT {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Unit DEFAULT_UNIT = Unit.MILLILITER;
    private static final Unit UPDATED_UNIT = Unit.DECILITER;

    private static final String ENTITY_API_URL = "/api/recipe-to-ingredients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecipeToIngredientRepository recipeToIngredientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeToIngredientMockMvc;

    private RecipeToIngredient recipeToIngredient;

    private RecipeToIngredient insertedRecipeToIngredient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeToIngredient createEntity() {
        return new RecipeToIngredient().quantity(DEFAULT_QUANTITY).unit(DEFAULT_UNIT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeToIngredient createUpdatedEntity() {
        return new RecipeToIngredient().quantity(UPDATED_QUANTITY).unit(UPDATED_UNIT);
    }

    @BeforeEach
    public void initTest() {
        recipeToIngredient = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRecipeToIngredient != null) {
            recipeToIngredientRepository.delete(insertedRecipeToIngredient);
            insertedRecipeToIngredient = null;
        }
    }

    @Test
    @Transactional
    void createRecipeToIngredient() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RecipeToIngredient
        var returnedRecipeToIngredient = om.readValue(
            restRecipeToIngredientMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeToIngredient)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RecipeToIngredient.class
        );

        // Validate the RecipeToIngredient in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRecipeToIngredientUpdatableFieldsEquals(
            returnedRecipeToIngredient,
            getPersistedRecipeToIngredient(returnedRecipeToIngredient)
        );

        insertedRecipeToIngredient = returnedRecipeToIngredient;
    }

    @Test
    @Transactional
    void createRecipeToIngredientWithExistingId() throws Exception {
        // Create the RecipeToIngredient with an existing ID
        recipeToIngredient.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeToIngredientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeToIngredient)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recipeToIngredient.setQuantity(null);

        // Create the RecipeToIngredient, which fails.

        restRecipeToIngredientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeToIngredient)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recipeToIngredient.setUnit(null);

        // Create the RecipeToIngredient, which fails.

        restRecipeToIngredientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeToIngredient)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecipeToIngredients() throws Exception {
        // Initialize the database
        insertedRecipeToIngredient = recipeToIngredientRepository.saveAndFlush(recipeToIngredient);

        // Get all the recipeToIngredientList
        restRecipeToIngredientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeToIngredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }

    @Test
    @Transactional
    void getRecipeToIngredient() throws Exception {
        // Initialize the database
        insertedRecipeToIngredient = recipeToIngredientRepository.saveAndFlush(recipeToIngredient);

        // Get the recipeToIngredient
        restRecipeToIngredientMockMvc
            .perform(get(ENTITY_API_URL_ID, recipeToIngredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeToIngredient.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRecipeToIngredient() throws Exception {
        // Get the recipeToIngredient
        restRecipeToIngredientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecipeToIngredient() throws Exception {
        // Initialize the database
        insertedRecipeToIngredient = recipeToIngredientRepository.saveAndFlush(recipeToIngredient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeToIngredient
        RecipeToIngredient updatedRecipeToIngredient = recipeToIngredientRepository.findById(recipeToIngredient.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecipeToIngredient are not directly saved in db
        em.detach(updatedRecipeToIngredient);
        updatedRecipeToIngredient.quantity(UPDATED_QUANTITY).unit(UPDATED_UNIT);

        restRecipeToIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecipeToIngredient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRecipeToIngredient))
            )
            .andExpect(status().isOk());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecipeToIngredientToMatchAllProperties(updatedRecipeToIngredient);
    }

    @Test
    @Transactional
    void putNonExistingRecipeToIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeToIngredient.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeToIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeToIngredient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeToIngredient))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipeToIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeToIngredient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeToIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeToIngredient))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipeToIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeToIngredient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeToIngredientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeToIngredient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecipeToIngredientWithPatch() throws Exception {
        // Initialize the database
        insertedRecipeToIngredient = recipeToIngredientRepository.saveAndFlush(recipeToIngredient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeToIngredient using partial update
        RecipeToIngredient partialUpdatedRecipeToIngredient = new RecipeToIngredient();
        partialUpdatedRecipeToIngredient.setId(recipeToIngredient.getId());

        partialUpdatedRecipeToIngredient.quantity(UPDATED_QUANTITY).unit(UPDATED_UNIT);

        restRecipeToIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeToIngredient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipeToIngredient))
            )
            .andExpect(status().isOk());

        // Validate the RecipeToIngredient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeToIngredientUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRecipeToIngredient, recipeToIngredient),
            getPersistedRecipeToIngredient(recipeToIngredient)
        );
    }

    @Test
    @Transactional
    void fullUpdateRecipeToIngredientWithPatch() throws Exception {
        // Initialize the database
        insertedRecipeToIngredient = recipeToIngredientRepository.saveAndFlush(recipeToIngredient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeToIngredient using partial update
        RecipeToIngredient partialUpdatedRecipeToIngredient = new RecipeToIngredient();
        partialUpdatedRecipeToIngredient.setId(recipeToIngredient.getId());

        partialUpdatedRecipeToIngredient.quantity(UPDATED_QUANTITY).unit(UPDATED_UNIT);

        restRecipeToIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeToIngredient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipeToIngredient))
            )
            .andExpect(status().isOk());

        // Validate the RecipeToIngredient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeToIngredientUpdatableFieldsEquals(
            partialUpdatedRecipeToIngredient,
            getPersistedRecipeToIngredient(partialUpdatedRecipeToIngredient)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRecipeToIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeToIngredient.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeToIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recipeToIngredient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeToIngredient))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecipeToIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeToIngredient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeToIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeToIngredient))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecipeToIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeToIngredient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeToIngredientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recipeToIngredient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeToIngredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecipeToIngredient() throws Exception {
        // Initialize the database
        insertedRecipeToIngredient = recipeToIngredientRepository.saveAndFlush(recipeToIngredient);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recipeToIngredient
        restRecipeToIngredientMockMvc
            .perform(delete(ENTITY_API_URL_ID, recipeToIngredient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recipeToIngredientRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected RecipeToIngredient getPersistedRecipeToIngredient(RecipeToIngredient recipeToIngredient) {
        return recipeToIngredientRepository.findById(recipeToIngredient.getId()).orElseThrow();
    }

    protected void assertPersistedRecipeToIngredientToMatchAllProperties(RecipeToIngredient expectedRecipeToIngredient) {
        assertRecipeToIngredientAllPropertiesEquals(expectedRecipeToIngredient, getPersistedRecipeToIngredient(expectedRecipeToIngredient));
    }

    protected void assertPersistedRecipeToIngredientToMatchUpdatableProperties(RecipeToIngredient expectedRecipeToIngredient) {
        assertRecipeToIngredientAllUpdatablePropertiesEquals(
            expectedRecipeToIngredient,
            getPersistedRecipeToIngredient(expectedRecipeToIngredient)
        );
    }
}
