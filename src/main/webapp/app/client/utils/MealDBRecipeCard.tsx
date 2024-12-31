import '../clientcss.css';

import React from 'react';
import { Button, Card, CardBody, CardTitle, Col, ListGroup, ListGroupItem, Row } from 'reactstrap';
import { IRecipeMealDB } from 'app/client/reducers/recipe-type';

interface IRecipeCardProps {
  recipe: IRecipeMealDB;
  getRandomRecipe?: () => void;
  isNotSearch: boolean;
}

export default function MealDBRecipeCard(props: IRecipeCardProps) {
  const { recipe, getRandomRecipe, isNotSearch } = props;
  return (
    <>
      {isNotSearch && (
        <div className="d-flex justify-content-center">
          <Button outline color="success" className="shadow-sm mb-3 custom-width" onClick={() => getRandomRecipe()}>
            Get Another Recipe
          </Button>
        </div>
      )}
      <Card key={recipe.idMeal} body outline color="secondary" className="shadow-lg custom-width m-auto p-4">
        <CardTitle tag="h5" className="text-center fw-bold">
          {recipe.strMeal}
        </CardTitle>
        <Row>
          <Col xs={12} md={6} className="mb-3 mt-md-5">
            <img alt={recipe.strMeal} src={recipe.strMealThumb} className="img-fluid rounded" />
            <div className="m-1 text-start">
              <span className="fw-bold">Category:</span> {recipe.strCategory}
            </div>
            <div className="m-1 text-start">
              <span className="fw-bold">Cuisine:</span> {recipe.strArea}
            </div>
          </Col>
          <Col xs={12} md={6} className="mb-3 mt-2">
            <h6 className="fw-bold">📝 Ingredients:</h6>
            <ListGroup flush className="text-start w-auto n">
              {Array.from({ length: 20 }, (_, i) => i + 1).map(index => {
                const ingredient = recipe[`strIngredient${index}`];
                const measure = recipe[`strMeasure${index}`];
                if (!ingredient) return null;
                return (
                  <ListGroupItem key={ingredient}>
                    {measure} - {ingredient}
                  </ListGroupItem>
                );
              })}
            </ListGroup>
          </Col>
        </Row>
        <CardBody>
          <h6 className="fw-bold">📘 Instructions:</h6>
          <div className="text-start m-2 ">{recipe.strInstructions}</div>
          {recipe.strYoutube && (
            <Button outline block color="info" className="shadow-sm mt-3" href={recipe.strYoutube} target="_blank">
              📺 Watch Instruction Video
            </Button>
          )}
        </CardBody>
      </Card>
    </>
  );
}
