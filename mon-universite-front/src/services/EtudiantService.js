import axios from 'axios';

// L'URL de base : /etudiant (définie dans le contrôleur Spring)
const API_URL = process.env.REACT_APP_API_URL || '/etudiant';

// GET: Tous les étudiants (GET /etudiant)
export const getAllEtudiants = () => axios.get(`${API_URL}`);

// GET: Un seul étudiant par ID (GET /etudiant/{id})
export const getEtudiant = id => axios.get(`${API_URL}/${id}`);

// POST: Ajouter un étudiant (POST /etudiant)
export const addEtudiant = etudiant => axios.post(`${API_URL}`, etudiant);

// PUT: Modifier un étudiant (PUT /etudiant)
export const updateEtudiant = etudiant => axios.put(`${API_URL}`, etudiant);

// DELETE: Supprimer un étudiant (DELETE /etudiant/{id})
export const deleteEtudiant = id => axios.delete(`${API_URL}/${id}`);

// GET: Récupérer les étudiants par école (GET /etudiant/ecole/{ecole})
export const getEtudiantsByEcole = ecole => axios.get(`${API_URL}/ecole/${ecole}`);

export const fetchEmails = async () => {
    const response = await axios.get(API_URL);
    return response.data.map((etudiant) => etudiant.email || etudiant.emailEt);
  };