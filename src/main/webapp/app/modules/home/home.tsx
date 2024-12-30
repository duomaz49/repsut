import './home.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Alert, Col, Row } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row className="p-5 m-5">
      <Col md="12">
        <h1 className="display-4 text-center">
          <Translate contentKey="home.title">Welcome to Repsut</Translate>
        </h1>
        <p className="lead text-center">
          <Translate contentKey="home.intro">The only recipe app you will ever need!</Translate>
        </p>
        <div className="d-flex justify-content-center">
          <img src="../../../content/images/Repsut.png" alt="Repsut brand logo" className="p-4 m-4 repsut" />
        </div>
        {account?.login && (
          <div className="d-flex justify-content-center">
            <Alert color="success" className="text-center custom-width">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
