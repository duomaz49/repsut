import React, { useState } from 'react';
import { IRecipe } from 'app/shared/model/recipe.model';
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { Button, Card, CardBody, Col, ListGroup, ListGroupItem, Row } from 'reactstrap';
import DeleteRecipe from 'app/client/crud-recipe/DeleteRecipe';

interface IRecipeCardProps {
  recipe: IRecipe;
  toggleRecipeDetailModal?: () => void;
}

export default function RecipeCard(props: IRecipeCardProps) {
  const { recipe, toggleRecipeDetailModal } = props;
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState<boolean>(false);
  const toggleDeleteModal = () => setIsDeleteModalOpen(!isDeleteModalOpen);

  return (
    <Row>
      <h6 className="text-center">
        <strong>Name:</strong> {recipe?.name}
      </h6>
      <hr />
      <Col xs={12} md={6} className="mb-3">
        <strong>🎯 Information:</strong>
        <Card>
          <CardBody>
            {recipe?.servings && (
              <div>
                <strong>Servings:</strong> {recipe.servings} people
              </div>
            )}
            {recipe?.cookTime && (
              <div>
                <strong>Cook time:</strong> {recipe.cookTime} minutes
              </div>
            )}
            <div>
              <strong>Created:</strong> {convertDateTimeFromServer(recipe?.createdDate)}
            </div>
            <div>
              <strong>Categories:</strong> {recipe?.categories?.map(category => category.name).join(', ')}
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
          <strong>📝 Ingredients:</strong>
          {recipe?.recipeToIngredients.map((obj, i) => (
            <ListGroupItem key={obj.id}>
              <span>
                {i + 1}. {obj.quantity} {obj.unit} {obj.ingredient?.name}
              </span>
            </ListGroupItem>
          ))}
        </ListGroup>
      </Col>
      <hr />
      <h6 className="fw-bold">
        <strong>📘Instructions:</strong>
      </h6>
      <div>{recipe?.instructions}</div>
      <Button outline block color="danger" className="shadow-sm mt-3" onClick={toggleDeleteModal}>
        🗑️ Delete Recipe
      </Button>
      <DeleteRecipe
        recipe={recipe}
        isDeleteModalOpen={isDeleteModalOpen}
        toggleDeleteModal={toggleDeleteModal}
        toggleRecipeModal={toggleRecipeDetailModal}
      />
    </Row>
  );
}
