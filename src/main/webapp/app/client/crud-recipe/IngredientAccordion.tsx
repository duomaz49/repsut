import React, { useState } from 'react';
import { Accordion, AccordionBody, AccordionHeader, AccordionItem, Button, FormGroup, Input, Label } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faPlus } from '@fortawesome/free-solid-svg-icons';
import { Unit } from 'app/shared/model/enumerations/unit.model';
import { translate, Translate } from 'react-jhipster';

interface IIngredientAccordionProps {
  ingredients: any[];
  setIngredients: (ingredients: object[]) => void;
}

export default function IngredientAccordion(props: IIngredientAccordionProps) {
  const { ingredients, setIngredients } = props;

  const [open, setOpen] = useState<string>('');
  const toggleAccordion = (id: string) => {
    setOpen(id === open ? '' : id);
  };

  const addAccordion = () => {
    const newIngredient = {
      id: ingredients.length,
      name: null,
      quantity: null,
      unit: null,
    };
    setIngredients([...ingredients, newIngredient]);
    setOpen((ingredients.length + 1).toString());
  };

  const deleteAccordion = (id: number) => {
    const newIngredients = ingredients.filter(ingredient => ingredient.id !== id);
    setIngredients(newIngredients);
  };

  const handleChange = (id, event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    const newIngredients = ingredients.map((ingredient, i) => {
      if (i === Number(id)) {
        return { ...ingredient, [name]: value };
      }
      return ingredient;
    });
    setIngredients(newIngredients);
  };

  console.log(ingredients);
  return (
    <div>
      <Button color="warning" outline block className="mb-2" onClick={addAccordion}>
        <Translate contentKey="clientRecipe.ingredients.addIngredient">Add ingredient</Translate> <FontAwesomeIcon icon={faPlus} />
      </Button>
      <Accordion open={open} toggle={toggleAccordion} className="mb-2">
        {ingredients.map((ingredient, i) => (
          <AccordionItem key={i}>
            <AccordionHeader targetId={i.toString()}>
              <div className="d-flex justify-content-between align-items-center w-100 me-3">
                <span>
                  {ingredient.name ?? <Translate contentKey="clientRecipe.ingredients.name" />}{' '}
                  {ingredient.quantity ?? <Translate contentKey="clientRecipe.ingredients.quantity" />}{' '}
                  {ingredient.unit ? (
                    <Translate contentKey={`clientRecipe.ingredients.units.${ingredient.unit}`} />
                  ) : (
                    <Translate contentKey="clientRecipe.ingredients.unit" />
                  )}
                </span>
                <div className="btn btn-outline-danger btn-sm ms-3 pointer" role="button" onClick={() => deleteAccordion(ingredient.id)}>
                  <FontAwesomeIcon icon={faTrash} />
                </div>
              </div>
            </AccordionHeader>
            <AccordionBody accordionId={i.toString()}>
              <FormGroup className="mb-1 px-2 text-start">
                <Label for="name" className="fw-bold ms-1">
                  <Translate contentKey="clientRecipe.ingredients.name">Name</Translate>
                </Label>
                <Input
                  id="name"
                  name="name"
                  value={ingredient.name}
                  placeholder={translate('clientRecipe.ingredients.namePlaceholder')}
                  type="text"
                  onChange={e => handleChange(i, e)}
                  required
                />
              </FormGroup>
              <FormGroup className="mb-1 px-2 text-start">
                <Label for="quantity" className="fw-bold ms-1">
                  <Translate contentKey="clientRecipe.ingredients.quantity">Quantity</Translate>
                </Label>
                <Input
                  id="quantity"
                  name="quantity"
                  value={ingredient.quantity}
                  placeholder={translate('clientRecipe.ingredients.quantityPlaceholder')}
                  type="number"
                  onChange={e => handleChange(i, e)}
                  required
                />
              </FormGroup>

              <FormGroup className="mb-1 px-2 text-start">
                <Label for="unit" className="fw-bold ms-1">
                  <Translate contentKey="clientRecipe.ingredients.unit">Unit</Translate>
                </Label>
                <Input
                  id="unit"
                  name="unit"
                  placeholder={translate('clientRecipe.ingredients.unitPlaceholder')}
                  value={ingredient.unit}
                  type="select"
                  onChange={e => handleChange(i, e)}
                >
                  <option value="" disabled>
                    <Translate contentKey="clientRecipe.ingredients.unitPlaceholder">Select the unit of the ingredient*</Translate>
                  </option>
                  {Object.values(Unit).map((unit, idx) => (
                    <option key={idx} value={unit}>
                      <Translate contentKey={`clientRecipe.ingredients.units.${unit}`} />
                    </option>
                  ))}
                </Input>
              </FormGroup>
            </AccordionBody>
          </AccordionItem>
        ))}
      </Accordion>
    </div>
  );
}
