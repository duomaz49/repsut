import React from 'react';

import { ListGroup, ListGroupItem } from 'reactstrap';
import { Link } from 'react-router-dom';
interface MenuItemsProps {
  items: any[];
  toggleOffCanvas: () => void;
  selected?: string;
}

export default function MenuItems(props: MenuItemsProps) {
  const { items, toggleOffCanvas, selected } = props;
  return (
    <>
      <ListGroup className="list-group ">
        {items?.map((item, i) => (
          <ListGroupItem
            key={i}
            onClick={toggleOffCanvas}
            className={`${selected === item ? 'bg-secondary-subtle' : 'btn btn-light'} d-flex justify-content-center align-items-center border rounded-pill mb-2 shadow-sm`}
          >
            {item.name}
            <Link to={item.path} className="stretched-link" />
          </ListGroupItem>
        ))}
      </ListGroup>
    </>
  );
}
