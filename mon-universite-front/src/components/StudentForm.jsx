import React, { useState, useEffect } from 'react';

import { Form, Button } from 'react-bootstrap';
import { addEtudiant, getEtudiant, updateEtudiant } from '../services/EtudiantService';
import { useNavigate, useParams } from 'react-router-dom';

function StudentForm() {
  const [etudiant, setEtudiant] = useState({
    nomEt: '',
    prenomEt: '',
    cin: '',
    ecole: '',
    emailEt: '',            // ✅ Ajout de l'email dans le state
    dateNaissance: ''
  });

  const navigate = useNavigate();
  const { id } = useParams();
  const isEdit = Boolean(id);

  useEffect(() => {
    if (isEdit) {
      getEtudiant(id).then(res => {
        const data = res.data;
        data.dateNaissance = data.dateNaissance.split('T')[0];
        setEtudiant(data);
      });
    }
  }, [id, isEdit]);

  const handleChange = e =>
    setEtudiant({ ...etudiant, [e.target.name]: e.target.value });

  const handleSubmit = e => {
    e.preventDefault();
    const action = isEdit ? updateEtudiant : addEtudiant;
    action(etudiant).then(() => navigate('/'));
  };

  return (
    <div>
      <h2>{isEdit ? 'Modifier' : 'Ajouter'} Étudiant</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Nom</Form.Label>
          <Form.Control name="nomEt" value={etudiant.nomEt} onChange={handleChange} required />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Prénom</Form.Label>
          <Form.Control name="prenomEt" value={etudiant.prenomEt} onChange={handleChange} required />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Mail</Form.Label>
          <Form.Control name="emailEt" value={etudiant.emailEt} onChange={handleChange} required />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>CIN</Form.Label>
          <Form.Control name="cin" type="number" value={etudiant.cin} onChange={handleChange} required />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>École</Form.Label>
          <Form.Control name="ecole" value={etudiant.ecole} onChange={handleChange} required />
        </Form.Group>
       
        <Form.Group className="mb-3">
          <Form.Label>Date de Naissance</Form.Label>
          <Form.Control name="dateNaissance" type="date" value={etudiant.dateNaissance} onChange={handleChange} required />
        </Form.Group>
        <Button variant="primary" type="submit">
          {isEdit ? 'Mettre à jour' : 'Ajouter'}
        </Button>
      </Form>
    </div>
  );
}

export default StudentForm;
