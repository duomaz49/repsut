import React from 'react';
import MenuList from './MenuItems';

interface MenuProps {
  toggleOffCanvas: () => void;
}

export default function Menu(props: MenuProps) {
  const { toggleOffCanvas } = props;
  return (
    <MenuList
      items={[
        { name: 'Home', path: '/' },
        { name: 'Random Recipe', path: '/random-recipe' },
        { name: 'Search for recipe', path: '/search-recipe' },
        { name: 'Create recipe', path: '/create-recipe' },
        { name: 'Update recipe', path: '/update-recipe' },
        { name: 'List your recipes', path: '/list-recipes' },
      ]}
      toggleOffCanvas={toggleOffCanvas}
    />
  );
}
