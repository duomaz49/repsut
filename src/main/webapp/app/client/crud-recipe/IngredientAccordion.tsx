import React, { useState } from 'react';
import { Accordion, AccordionBody, AccordionHeader, AccordionItem, Button, FormGroup, Input, Label } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faPlus } from '@fortawesome/free-solid-svg-icons';
import { Unit } from 'app/shared/model/enumerations/unit.model';

interface IIngredientAccordionProps {
  ingredients: any[];
  setIngredients: (ingredients: object[]) => void;
  create: boolean;
}

export default function IngredientAccordion(props: IIngredientAccordionProps) {
  const { ingredients, setIngredients, create } = props;

  const [open, setOpen] = useState<string>('');
  const toggleAccordion = (id: string) => {
    setOpen(id === open ? '' : id);
  };

  const addAccordion = () => {
    const newIngredient = {
      id: ingredients.length,
      name: '',
      quantity: null,
      unit: '',
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

  return (
    <div>
      {create && (
        <Button color="warning" outline block className="mb-2" onClick={addAccordion}>
          Add ingredient <FontAwesomeIcon icon={faPlus} />
        </Button>
      )}
      <Accordion open={open} toggle={toggleAccordion} className="mb-2">
        {ingredients.map((ingredient, i) => (
          <AccordionItem key={i}>
            <AccordionHeader targetId={i.toString()}>
              <div className="d-flex justify-content-between align-items-center w-100 me-3">
                <span>
                  {ingredient.name ?? null} {ingredient.quantity ?? null} {ingredient.unit.toLowerCase() ?? null}
                </span>
                {create && (
                  <div className="btn btn-outline-danger btn-sm ms-3 pointer" role="button" onClick={() => deleteAccordion(ingredient.id)}>
                    <FontAwesomeIcon icon={faTrash} />
                  </div>
                )}
              </div>
            </AccordionHeader>
            <AccordionBody accordionId={i.toString()}>
              <FormGroup className="mb-1 px-2 text-start">
                <Label for="name" className="fw-bold ms-1">
                  Name*
                </Label>
                <Input
                  id="name"
                  name="name"
                  value={ingredient.name}
                  placeholder="Name"
                  type="text"
                  onChange={e => handleChange(ingredient.id, e)}
                  required
                />
              </FormGroup>
              <FormGroup className="mb-1 px-2 text-start">
                <Label for="quantity" className="fw-bold ms-1">
                  Quantity*
                </Label>
                <Input
                  id="quantity"
                  name="quantity"
                  value={ingredient.quantity}
                  placeholder="Quantity"
                  type="number"
                  onChange={e => handleChange(ingredient.id, e)}
                  required
                />
              </FormGroup>

              <FormGroup className="mb-1 px-2 text-start">
                <Label for="unit" className="fw-bold ms-1">
                  Unit
                </Label>
                <Input
                  id="unit"
                  name="unit"
                  placeholder="Unit"
                  value={ingredient.unit}
                  type="select"
                  onChange={e => handleChange(ingredient.id, e)}
                >
                  <option value="" disabled>
                    Select Unit
                  </option>
                  {Object.values(Unit).map((unit, idx) => (
                    <option key={idx} value={unit}>
                      {unit.toLowerCase()}
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
