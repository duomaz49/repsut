package com.tuomas.repsut.web.rest;

import static com.tuomas.repsut.domain.RecipeAsserts.*;
import static com.tuomas.repsut.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuomas.repsut.IntegrationTest;
import com.tuomas.repsut.domain.Recipe;
import com.tuomas.repsut.repository.RecipeRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RecipeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RecipeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTIONS = "BBBBBBBBBB";

    private static final Integer DEFAULT_COOK_TIME = 1;
    private static final Integer UPDATED_COOK_TIME = 2;

    private static final Integer DEFAULT_SERVINGS = 1;
    private static final Integer UPDATED_SERVINGS = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/recipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeMockMvc;

    private Recipe recipe;

    private Recipe insertedRecipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createEntity() {
        return new Recipe()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .instructions(DEFAULT_INSTRUCTIONS)
            .cookTime(DEFAULT_COOK_TIME)
            .servings(DEFAULT_SERVINGS)
            .createdDate(DEFAULT_CREATED_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createUpdatedEntity() {
        return new Recipe()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .instructions(UPDATED_INSTRUCTIONS)
            .cookTime(UPDATED_COOK_TIME)
            .servings(UPDATED_SERVINGS)
            .createdDate(UPDATED_CREATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        recipe = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRecipe != null) {
            recipeRepository.delete(insertedRecipe);
            insertedRecipe = null;
        }
    }

    @Test
    @Transactional
    void createRecipe() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Recipe
        var returnedRecipe = om.readValue(
            restRecipeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipe)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Recipe.class
        );

        // Validate the Recipe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRecipeUpdatableFieldsEquals(returnedRecipe, getPersistedRecipe(returnedRecipe));

        insertedRecipe = returnedRecipe;
    }

    @Test
    @Transactional
    void createRecipeWithExistingId() throws Exception {
        // Create the Recipe with an existing ID
        recipe.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipe)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recipe.setName(null);

        // Create the Recipe, which fails.

        restRecipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipe)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServingsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recipe.setServings(null);

        // Create the Recipe, which fails.

        restRecipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipe)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recipe.setCreatedDate(null);

        // Create the Recipe, which fails.

        restRecipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipe)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecipes() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].instructions").value(hasItem(DEFAULT_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].cookTime").value(hasItem(DEFAULT_COOK_TIME)))
            .andExpect(jsonPath("$.[*].servings").value(hasItem(DEFAULT_SERVINGS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecipesWithEagerRelationshipsIsEnabled() throws Exception {
        when(recipeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecipeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(recipeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecipesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(recipeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecipeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(recipeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRecipe() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        // Get the recipe
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL_ID, recipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipe.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.instructions").value(DEFAULT_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.cookTime").value(DEFAULT_COOK_TIME))
            .andExpect(jsonPath("$.servings").value(DEFAULT_SERVINGS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRecipe() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecipe() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipe
        Recipe updatedRecipe = recipeRepository.findById(recipe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecipe are not directly saved in db
        em.detach(updatedRecipe);
        updatedRecipe
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .instructions(UPDATED_INSTRUCTIONS)
            .cookTime(UPDATED_COOK_TIME)
            .servings(UPDATED_SERVINGS)
            .createdDate(UPDATED_CREATED_DATE);

        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecipeToMatchAllProperties(updatedRecipe);
    }

    @Test
    @Transactional
    void putNonExistingRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(put(ENTITY_API_URL_ID, recipe.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipe)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecipeWithPatch() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipe using partial update
        Recipe partialUpdatedRecipe = new Recipe();
        partialUpdatedRecipe.setId(recipe.getId());

        partialUpdatedRecipe.name(UPDATED_NAME).instructions(UPDATED_INSTRUCTIONS).createdDate(UPDATED_CREATED_DATE);

        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRecipe, recipe), getPersistedRecipe(recipe));
    }

    @Test
    @Transactional
    void fullUpdateRecipeWithPatch() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipe using partial update
        Recipe partialUpdatedRecipe = new Recipe();
        partialUpdatedRecipe.setId(recipe.getId());

        partialUpdatedRecipe
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .instructions(UPDATED_INSTRUCTIONS)
            .cookTime(UPDATED_COOK_TIME)
            .servings(UPDATED_SERVINGS)
            .createdDate(UPDATED_CREATED_DATE);

        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeUpdatableFieldsEquals(partialUpdatedRecipe, getPersistedRecipe(partialUpdatedRecipe));
    }

    @Test
    @Transactional
    void patchNonExistingRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recipe.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecipe() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recipe
        restRecipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, recipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recipeRepository.count();
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

    protected Recipe getPersistedRecipe(Recipe recipe) {
        return recipeRepository.findById(recipe.getId()).orElseThrow();
    }

    protected void assertPersistedRecipeToMatchAllProperties(Recipe expectedRecipe) {
        assertRecipeAllPropertiesEquals(expectedRecipe, getPersistedRecipe(expectedRecipe));
    }

    protected void assertPersistedRecipeToMatchUpdatableProperties(Recipe expectedRecipe) {
        assertRecipeAllUpdatablePropertiesEquals(expectedRecipe, getPersistedRecipe(expectedRecipe));
    }
}
