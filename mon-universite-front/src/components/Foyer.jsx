import React, { useEffect, useState } from 'react';
import axios from 'axios';

const getAuthHeader = () => {
  const token = localStorage.getItem('token');
  return { headers: { Authorization: `Bearer ${token}` } };
};

const Foyer = () => {
  const [foyers, setFoyers] = useState([]);
  const [newFoyer, setNewFoyer] = useState({
    nomFoyer: '',
    capaciteFoyer: ''
  });

  const fetchFoyers = async () => {
    try {
      console.log(getAuthHeader()); // debug
      const res = await axios.get('http://localhost:8090/foyer', getAuthHeader());
      setFoyers(res.data);
    } catch (err) {
      console.error('Erreur de chargement des foyers :', err.response);
    }
  };

  const addFoyer = async () => {
    try {
      await axios.post('http://localhost:8090/foyer', newFoyer, getAuthHeader());
      setNewFoyer({ nomFoyer: '', capaciteFoyer: '' });
      fetchFoyers();
    } catch (err) {
      console.error('Erreur d\'ajout du foyer :', err);
    }
  };

  const deleteFoyer = async (id) => {
    try {
      await axios.delete(`http://localhost:8090/foyer/${id}`, getAuthHeader());
      fetchFoyers();
    } catch (err) {
      console.error('Erreur de suppression du foyer :', err);
    }
  };

  useEffect(() => {
    fetchFoyers();
  }, []);

  return (
    <div>
      <h2>Liste des Foyers</h2>

      <div>
        <input
          type="text"
          placeholder="Nom du foyer"
          value={newFoyer.nomFoyer}
          onChange={(e) => setNewFoyer({ ...newFoyer, nomFoyer: e.target.value })}
        />
        <input
          type="number"
          placeholder="Capacité"
          value={newFoyer.capaciteFoyer}
          onChange={(e) => setNewFoyer({ ...newFoyer, capaciteFoyer: e.target.value })}
        />
        <button onClick={addFoyer}>Ajouter</button>
      </div>

      <ul>
        {foyers.map((foyer) => (
          <li key={foyer.id}>
            {foyer.nomFoyer} — Capacité : {foyer.capaciteFoyer}
            <button onClick={() => deleteFoyer(foyer.id)}>Supprimer</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Foyer;
