import React, { useState, useEffect } from 'react';
import axios from 'axios';
// Axios configuration
axios.defaults.withCredentials = true;
// Header avec token
const getAuthHeader = () => {
  const token = localStorage.getItem('token');
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    withCredentials: true  // Important for CORS with credentials
  };
};

const Reservation = () => {
  const [reservations, setReservations] = useState([]);
  const [newReservation, setNewReservation] = useState({
    anneeUniversitaire: '',
    estValide: false
  });

  const fetchReservations = async () => {
    try {
      const res = await axios.get('http://localhost:8222/reservation', getAuthHeader());
      setReservations(res.data);
    } catch (err) {
      console.error('Erreur de chargement des réservations :', err);
    }
  };

  const deleteReservation = async (id) => {
    try {
      await axios.delete(`http://localhost:8222/reservation/${id}`, getAuthHeader());
      fetchReservations();
    } catch (err) {
      console.error('Erreur de suppression :', err);
    }
  };

  const addReservation = async () => {
    try {
      await axios.post('http://localhost:8222/reservation', newReservation, getAuthHeader());
      setNewReservation({ anneeUniversitaire: '', estValide: false });
      fetchReservations();
    } catch (err) {
      console.error('Erreur d\'ajout :', err);
    }
  };

  useEffect(() => {
    fetchReservations();
  }, []);

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Gestion des Réservations</h2>

      <div style={styles.formContainer}>
        <div style={styles.inputGroup}>
          <label style={styles.label}>Année Universitaire:</label>
          <input
            type="date"
            value={newReservation.anneeUniversitaire}
            onChange={(e) =>
              setNewReservation({ ...newReservation, anneeUniversitaire: e.target.value })
            }
            style={styles.input}
          />
        </div>
        
        <div style={styles.checkboxGroup}>
          <input
            type="checkbox"
            id="estValide"
            checked={newReservation.estValide}
            onChange={(e) =>
              setNewReservation({ ...newReservation, estValide: e.target.checked })
            }
            style={styles.checkbox}
          />
          <label htmlFor="estValide" style={styles.checkboxLabel}>Validée</label>
        </div>

        <button onClick={addReservation} style={styles.addButton}>
          Ajouter Réservation
        </button>
      </div>

      <div style={styles.listContainer}>
        <h3 style={styles.subTitle}>Liste des Réservations</h3>
        {reservations.length === 0 ? (
          <p style={styles.noData}>Aucune réservation trouvée</p>
        ) : (
          <ul style={styles.list}>
            {reservations.map((r) => (
              <li key={r.id} style={styles.listItem}>
                <span style={styles.itemText}>
                  {r.anneeUniversitaire} - 
                  <span style={{ color: r.estValide ? '#4CAF50' : '#F44336' }}>
                    {r.estValide ? ' Validée' : ' Non validée'}
                  </span>
                </span>
                <button 
                  onClick={() => deleteReservation(r.id)} 
                  style={styles.deleteButton}
                >
                  Supprimer
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

const styles = {
  container: {
    maxWidth: '800px',
    margin: '0 auto',
    padding: '20px',
    fontFamily: 'Arial, sans-serif',
    backgroundColor: '#f9f9f9',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
  },
  title: {
    color: '#333',
    textAlign: 'center',
    marginBottom: '30px'
  },
  subTitle: {
    color: '#444',
    marginBottom: '15px',
    paddingBottom: '5px',
    borderBottom: '1px solid #ddd'
  },
  formContainer: {
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '5px',
    marginBottom: '30px',
    boxShadow: '0 1px 3px rgba(0,0,0,0.1)'
  },
  inputGroup: {
    marginBottom: '15px'
  },
  label: {
    display: 'block',
    marginBottom: '5px',
    fontWeight: 'bold',
    color: '#555'
  },
  input: {
    width: '100%',
    padding: '8px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '16px'
  },
  checkboxGroup: {
    display: 'flex',
    alignItems: 'center',
    marginBottom: '15px'
  },
  checkbox: {
    marginRight: '10px'
  },
  checkboxLabel: {
    color: '#555'
  },
  addButton: {
    backgroundColor: '#4CAF50',
    color: 'white',
    border: 'none',
    padding: '10px 15px',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '16px',
    transition: 'background-color 0.3s'
  },
  listContainer: {
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '5px',
    boxShadow: '0 1px 3px rgba(0,0,0,0.1)'
  },
  list: {
    listStyle: 'none',
    padding: 0
  },
  listItem: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px',
    borderBottom: '1px solid #eee',
    transition: 'background-color 0.2s'
  },
  itemText: {
    flex: 1
  },
  deleteButton: {
    backgroundColor: '#F44336',
    color: 'white',
    border: 'none',
    padding: '5px 10px',
    borderRadius: '3px',
    cursor: 'pointer',
    transition: 'background-color 0.3s'
  },
  noData: {
    color: '#777',
    textAlign: 'center',
    fontStyle: 'italic'
  }
};

export default Reservation;