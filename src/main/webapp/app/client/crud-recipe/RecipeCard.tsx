import React, { useState } from 'react';
import { IRecipe } from 'app/shared/model/recipe.model';
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { Button, Card, CardBody, Col, ListGroup, ListGroupItem, Row } from 'reactstrap';
import DeleteRecipe from 'app/client/crud-recipe/DeleteRecipe';
import { Translate } from 'react-jhipster';

interface IRecipeCardProps {
  recipe: IRecipe;
  toggleRecipeDetailModal?: () => void;
  openCreateOrEditModal?: (id: number) => void;
}

export default function RecipeCard(props: IRecipeCardProps) {
  const { recipe, toggleRecipeDetailModal, openCreateOrEditModal } = props;
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState<boolean>(false);
  const toggleDeleteModal = () => setIsDeleteModalOpen(!isDeleteModalOpen);

  return (
    <Row>
      <h6 className="text-center">
        <Translate contentKey="clientRecipe.recipeCard.name">
          <strong>Name:</strong>{' '}
        </Translate>
        {recipe?.name}
      </h6>
      <hr />
      <Col xs={12} md={6} className="mb-3">
        <Translate contentKey="clientRecipe.recipeCard.information">
          <strong>🎯 Information:</strong>
        </Translate>
        <Card>
          <CardBody>
            {recipe?.servings && (
              <div>
                <Translate contentKey="clientRecipe.recipeCard.servings" interpolate={{ param: recipe.servings.toString() }}>
                  <strong>Servings:</strong> {recipe.servings.toString()} people
                </Translate>
              </div>
            )}
            {recipe?.cookTime && (
              <div>
                <Translate contentKey="clientRecipe.recipeCard.cookTime" interpolate={{ param: recipe.cookTime.toString() }}>
                  <strong>Cook time:</strong> {recipe.cookTime.toString()} minutes
                </Translate>
              </div>
            )}
            <div>
              <Translate contentKey="clientRecipe.recipeCard.createdBy">
                <strong>Created:</strong>
              </Translate>
              {convertDateTimeFromServer(recipe?.createdDate)}
            </div>
            <div>
              <Translate contentKey="clientRecipe.recipeCard.categories">
                <strong>Categories:</strong>
              </Translate>
              {recipe?.categories?.map(category => category.name).join(', ')}
            </div>
            {recipe?.description && (
              <div>
                <strong>Description:</strong> {recipe.description}
              </div>
            )}
          </CardBody>
        </Card>
      </Col>
      <Col xs={12} md={6} className="mb-3">
        <ListGroup>
          <Translate contentKey="clientRecipe.recipeCard.ingredients">
            <strong>📝 Ingredients:</strong>
          </Translate>
          {recipe?.recipeToIngredients.map((obj, i) => (
            <ListGroupItem key={obj.id}>
              <span>
                {i + 1}. {obj.quantity} {<Translate contentKey={`clientRecipe.ingredients.units.${obj.unit}`} />} {obj.ingredient?.name}
              </span>
            </ListGroupItem>
          ))}
        </ListGroup>
      </Col>
      <hr />
      <h6 className="fw-bold">
        <Translate contentKey="clientRecipe.recipeCard.instructions">
          {' '}
          <strong>📘Instructions:</strong>
        </Translate>
      </h6>
      <div>{recipe?.instructions}</div>
      <div className="d-flex justify-content-between">
        <Button outline color="danger" className="shadow-sm mt-3" onClick={toggleDeleteModal}>
          <Translate contentKey="clientRecipe.recipeCard.deleteButton"> 🗑️ Delete Recipe</Translate>
        </Button>
        <Button outline color="info" className="shadow-sm mt-3" onClick={() => openCreateOrEditModal(recipe.id)}>
          <Translate contentKey="clientRecipe.recipeCard.editButton"> ✏️ Edit Recipe</Translate>
        </Button>
      </div>
      <DeleteRecipe
        recipe={recipe}
        isDeleteModalOpen={isDeleteModalOpen}
        toggleDeleteModal={toggleDeleteModal}
        toggleRecipeModal={toggleRecipeDetailModal}
      />
    </Row>
  );
}
