import '../clientcss.css';

import React from 'react';
import type { ReactNode } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';

interface OverlayProps {
  children?: ReactNode;
  isOpen?: boolean;
  toggle?: () => void;
  title?: string;
}

export default function Overlay(props: OverlayProps) {
  const { children, isOpen, toggle, title } = props;
  return (
    <Modal id="fancybox" isOpen={isOpen} toggle={toggle} keyboard={false}>
      <ModalHeader toggle={toggle}>{title}</ModalHeader>
      <ModalBody
        style={{
          overflowY: 'auto',
          overflowX: 'hidden',
        }}
      >
        {children &&
          React.Children.map(children, (child, index) => (
            <div key={index} className="w-100">
              {child}
            </div>
          ))}
      </ModalBody>
    </Modal>
  );
}
