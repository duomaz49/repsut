import React from 'react';

import { Nav, Navbar, NavbarBrand, NavbarToggler, Offcanvas, OffcanvasBody, OffcanvasHeader } from 'reactstrap';
import { useState } from 'react';
import Menu from './Menu';

export default function NavBar(args) {
  const [isOffCanvasOpen, setIsOffCanvasOpen] = useState(false);
  const toggleOffCanvas = () => setIsOffCanvasOpen(!isOffCanvasOpen);

  return (
    <Navbar {...args} color="light" light fixed="top" className="d-flex flex-nowrap justify-content-between align-items-center shadow-lg">
      <NavbarBrand href="/" className="d-flex align-items-center">
        <img src="../../../content/images/Repsut.png" alt="Repsut brand logo" style={{ width: '80px', height: '40px' }} className="me-2" />
      </NavbarBrand>
      <NavbarToggler onClick={toggleOffCanvas} />
      <Offcanvas isOpen={isOffCanvasOpen} direction="end" toggle={toggleOffCanvas}>
        <OffcanvasHeader className="bg-light" toggle={toggleOffCanvas}>
          Menu
        </OffcanvasHeader>
        <OffcanvasBody className="bg-light shadow">
          <Nav navbar>
            <Menu toggleOffCanvas={toggleOffCanvas} />
          </Nav>
        </OffcanvasBody>
      </Offcanvas>
    </Navbar>
  );
}
