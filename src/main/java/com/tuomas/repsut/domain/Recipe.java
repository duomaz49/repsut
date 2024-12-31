package com.tuomas.repsut.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Recipe.
 */
@Entity
@Table(name = "recipe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "cook_time")
    private Integer cookTime;

    @Column(name = "servings")
    private Integer servings;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recipe")
    @JsonIgnoreProperties(value = { "recipe" }, allowSetters = true)
    private Set<RecipeToIngredient> recipeToIngredients = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rel_recipe__category",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties(value = { "recipes" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Recipe name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Recipe description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public Recipe instructions(String instructions) {
        this.setInstructions(instructions);
        return this;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getCookTime() {
        return this.cookTime;
    }

    public Recipe cookTime(Integer cookTime) {
        this.setCookTime(cookTime);
        return this;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return this.servings;
    }

    public Recipe servings(Integer servings) {
        this.setServings(servings);
        return this;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Recipe createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<RecipeToIngredient> getRecipeToIngredients() {
        return this.recipeToIngredients;
    }

    public void setRecipeToIngredients(Set<RecipeToIngredient> recipeToIngredients) {
        if (this.recipeToIngredients != null) {
            this.recipeToIngredients.forEach(i -> i.setRecipe(null));
        }
        if (recipeToIngredients != null) {
            recipeToIngredients.forEach(i -> i.setRecipe(this));
        }
        this.recipeToIngredients = recipeToIngredients;
    }

    public Recipe recipeToIngredients(Set<RecipeToIngredient> recipeToIngredients) {
        this.setRecipeToIngredients(recipeToIngredients);
        return this;
    }

    public Recipe addRecipeToIngredient(RecipeToIngredient recipeToIngredient) {
        this.recipeToIngredients.add(recipeToIngredient);
        recipeToIngredient.setRecipe(this);
        return this;
    }

    public Recipe removeRecipeToIngredient(RecipeToIngredient recipeToIngredient) {
        this.recipeToIngredients.remove(recipeToIngredient);
        recipeToIngredient.setRecipe(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Recipe categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Recipe addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public Recipe removeCategory(Category category) {
        this.categories.remove(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        return getId() != null && getId().equals(((Recipe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", instructions='" + getInstructions() + "'" +
            ", cookTime=" + getCookTime() +
            ", servings=" + getServings() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
