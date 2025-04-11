import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Container, Navbar, Nav, NavDropdown } from 'react-bootstrap';
import StudentList from './components/StudentList';
import StudentForm from './components/StudentForm';
import HolidayList from './components/HolidayList';
import MailSender from './components/MailSender';

function App() {
  return (
    <Router>
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand as={Link} to="/">Gestion Étudiants</Navbar.Brand>
          <Nav className="me-auto">
            {/* Menu déroulant pour les liens */}
            <NavDropdown title="Gestion Étudiants" id="navbar-dropdown">
              <NavDropdown.Item as={Link} to="/">Accueil</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/add">Ajouter Étudiant</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/holidays">Jours Fériés</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/mail">Envoyer Un Email</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Container>
      </Navbar>

      <Container className="mt-4">
        <Routes>
          <Route path="/" element={<StudentList />} />
          <Route path="/add" element={<StudentForm />} />
          <Route path="/edit/:id" element={<StudentForm />} />
          <Route path="/holidays" element={<HolidayList />} /> 
          <Route path="/mail" element={<MailSender />} /> 
        </Routes>
      </Container>
    </Router>
  );
}

export default App;
