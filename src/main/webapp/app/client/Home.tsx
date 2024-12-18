import React from 'react';
import { Container } from 'reactstrap';

export default function Home() {
  return (
    <Container fluid className="vh-100 d-flex flex-column justify-content-center align-items-center">
      <div className="m-auto text-center">
        <h1 className="text-center">
          Welcome to <span className="text-success">Repsut</span>!
        </h1>
        <p>The only recipe app you will ever need!</p>
        <img
          src="../../../content/images/Repsut.png"
          alt="Repsut brand logo"
          style={{ width: '400px', height: '200px' }}
          className="me-2"
        />
      </div>
    </Container>
  );
}
