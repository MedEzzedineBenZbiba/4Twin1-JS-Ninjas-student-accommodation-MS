import React, { useState, useEffect } from 'react';
import { Table, Button, Form, InputGroup } from 'react-bootstrap';
import {
  getAllEtudiants,
  deleteEtudiant,
  getEtudiantsByEcole,
  getEtudiant
} from '../services/EtudiantService';
import { useNavigate } from 'react-router-dom';

function StudentList() {
  const [etudiants, setEtudiants] = useState([]);
  const [ecole, setEcole] = useState('');
  const [searchId, setSearchId] = useState('');
  const navigate = useNavigate();

  const fetchData = () => {
    getAllEtudiants()
      .then(res => setEtudiants(res.data))
      .catch(err => console.error(err));
  };

  const fetchEtudiantsByEcole = () => {
    if (ecole) {
      getEtudiantsByEcole(ecole)
        .then(res => setEtudiants(res.data))
        .catch(err => console.error(err));
    } else {
      fetchData();
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleDelete = id => {
    if (window.confirm('Voulez-vous vraiment supprimer cet étudiant ?')) {
      deleteEtudiant(id).then(() => fetchData());
    }
  };

  const handleSearchChange = (event) => {
    setEcole(event.target.value);
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    fetchEtudiantsByEcole();
  };

  const handleIdSearchSubmit = (e) => {
    e.preventDefault();
    if (searchId) {
      getEtudiant(searchId)
        .then(res => setEtudiants([res.data]))
        .catch(err => {
          console.error(err);
          alert("Étudiant non trouvé !");
        });
    } else {
      fetchData();
    }
  };

  return (
    <div className="container mt-4">
      <h2>Liste des Étudiants</h2>

      {/* Recherche par École */}
      <Form onSubmit={handleSearchSubmit} className="mb-3">
        <InputGroup>
          <Form.Control
            type="text"
            placeholder="Rechercher par École"
            value={ecole}
            onChange={handleSearchChange}
          />
          <Button variant="primary" type="submit">Rechercher</Button>
        </InputGroup>
      </Form>

      {/* Recherche par ID */}
      <Form onSubmit={handleIdSearchSubmit} className="mb-3">
        <InputGroup>
          <Form.Control
            type="number"
            placeholder="Rechercher par ID Étudiant"
            value={searchId}
            onChange={e => setSearchId(e.target.value)}
          />
          <Button variant="secondary" type="submit">Chercher ID</Button>
        </InputGroup>
      </Form>

      <Table striped bordered hover>
  <thead>
    <tr>
      <th>ID</th>
      <th>Nom</th>
      <th>Prénom</th>
      <th>CIN</th>
      <th>École</th>
      <th>Email</th> {/* ✅ Nouvelle colonne */}
      <th>Date Naissance</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    {etudiants.map(et => (
      <tr key={et.idEtudiant}>
        <td>{et.idEtudiant}</td>
        <td>{et.nomEt}</td>
        <td>{et.prenomEt}</td>
        <td>{et.cin}</td>
        <td>{et.ecole}</td>
        <td>{et.emailEt}</td> {/* ✅ Affichage de l'email */}
        <td>{new Date(et.dateNaissance).toLocaleDateString()}</td>
        <td>
          <Button variant="info" size="sm" onClick={() => navigate(`/edit/${et.idEtudiant}`)}>Éditer</Button>{' '}
          <Button variant="danger" size="sm" onClick={() => handleDelete(et.idEtudiant)}>Supprimer</Button>
        </td>
      </tr>
    ))}
  </tbody>
</Table>
    </div>
  );
}

export default StudentList;
