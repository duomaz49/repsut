package com.tuomas.repsut.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tuomas.repsut.domain.enumeration.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A RecipeToIngredient.
 */
@Entity
@Table(name = "recipe_to_ingredient")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecipeToIngredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private Unit unit;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "recipeToIngredients", "categories" }, allowSetters = true)
    private Recipe recipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RecipeToIngredient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public RecipeToIngredient quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public RecipeToIngredient unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public RecipeToIngredient ingredient(Ingredient ingredient) {
        this.setIngredient(ingredient);
        return this;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeToIngredient recipe(Recipe recipe) {
        this.setRecipe(recipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeToIngredient)) {
            return false;
        }
        return getId() != null && getId().equals(((RecipeToIngredient) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeToIngredient{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
