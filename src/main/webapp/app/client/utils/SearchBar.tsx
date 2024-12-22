import '../clientcss.css';

import React from 'react';
import { Input } from 'reactstrap';

interface SearchBarProps {
  query: string;
  setQuery: (query: string) => void;
  placeholder?: string;
}

export default function SearchBar(props: SearchBarProps) {
  const { query, setQuery, placeholder } = props;
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newQuery = e.target.value;
    setQuery(newQuery);
  };

  return (
    <Input className="custom-search-width shadow-sm mb-4" type="search" placeholder={placeholder} onChange={handleChange} value={query} />
  );
}
