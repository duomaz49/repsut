import '../clientcss.css';

import React from 'react';

interface SkeletonLoaderProps {
  length: number;
}

export default function SkeletonLoader(props: SkeletonLoaderProps) {
  const { length } = props;
  return (
    <p className="placeholder-glow mt-4">
      {Array.from({ length }).map((_, i) => (
        <span key={i} className="placeholder col-12 bg-success m-2 shadow"></span>
      ))}
    </p>
  );
}
