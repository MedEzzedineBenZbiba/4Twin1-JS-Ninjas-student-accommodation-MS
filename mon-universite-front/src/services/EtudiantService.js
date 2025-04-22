import axios from 'axios';
const API_URL = import.meta.env.VITE_API_URL;
// Fonction utilitaire pour obtenir le token (à adapter selon ton système de stockage)
const getAuthHeader = () => {
  const token = localStorage.getItem('token'); // ou sessionStorage, ou un context
  return { headers: { Authorization: `Bearer ${token}` } };
};

export const getAllEtudiants = () => axios.get(`${API_URL}`, getAuthHeader());

export const getEtudiant = (id) => axios.get(`${API_URL}/${id}`, getAuthHeader());

export const addEtudiant = (etudiant) => axios.post(`${API_URL}`, etudiant, getAuthHeader());

export const updateEtudiant = (etudiant) => axios.put(`${API_URL}`, etudiant, getAuthHeader());

export const deleteEtudiant = (id) => axios.delete(`${API_URL}/${id}`, getAuthHeader());

export const getEtudiantsByEcole = (ecole) => axios.get(`${API_URL}/ecole/${ecole}`, getAuthHeader());

export const fetchEmails = async () => {
  const response = await axios.get(API_URL, getAuthHeader());
  return response.data.map((etudiant) => etudiant.email || etudiant.emailEt);
};
