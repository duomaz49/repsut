import '../clientcss.css';

import React from 'react';

interface ISkeletonLoaderProps {
  length: number;
}

export default function SkeletonLoader(props: ISkeletonLoaderProps) {
  const { length } = props;
  return (
    <div className="placeholder-glow mt-4">
      {Array.from({ length }).map((_, i) => (
        <span key={i} className="placeholder w-100 bg-success m-2 shadow"></span>
      ))}
    </div>
  );
}
