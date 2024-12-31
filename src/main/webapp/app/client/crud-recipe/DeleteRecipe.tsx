import '../clientcss.css';
import React from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch } from 'app/config/store';
import { deleteEntity } from 'app/entities/recipe/recipe.reducer';
import { IRecipe } from 'app/shared/model/recipe.model';

interface IDeleteRecipeProps {
  recipe: IRecipe;
  isDeleteModalOpen: boolean;
  toggleDeleteModal: () => void;
  toggleRecipeModal?: () => void;
}

export default function DeleteRecipe(props: IDeleteRecipeProps) {
  const { recipe, isDeleteModalOpen, toggleDeleteModal, toggleRecipeModal } = props;
  const dispatch = useAppDispatch();

  const confirmDelete = () => {
    dispatch(deleteEntity(recipe.id));
    toggleDeleteModal();
    toggleRecipeModal();
  };

  const handleClose = () => {
    toggleDeleteModal();
  };

  return (
    <Modal isOpen={isDeleteModalOpen} toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="recipeDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="repsutApp.recipe.delete.question">
        <Translate contentKey="repsutApp.recipe.delete.question" interpolate={{ name: recipe?.name }}>
          Are you sure you want to delete this Recipe?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-recipe" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
}
