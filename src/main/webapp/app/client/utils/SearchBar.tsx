import '../clientcss.css';

import React from 'react';
import { Input } from 'reactstrap';

interface ISearchBarProps {
  query: string;
  setQuery: (query: string) => void;
  placeholder?: string;
  onSearch?: () => void;
}

export default function SearchBar(props: ISearchBarProps) {
  const { query, setQuery, placeholder, onSearch } = props;
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newQuery = e.target.value;
    setQuery(newQuery);
  };
  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      onSearch();
    }
  };

  return (
    <Input
      className="custom-search-width shadow-sm mb-3"
      type="search"
      placeholder={placeholder}
      onChange={handleChange}
      value={query}
      onKeyDown={onSearch ? handleKeyDown : undefined}
    />
  );
}
