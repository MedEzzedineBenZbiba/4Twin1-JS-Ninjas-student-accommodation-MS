import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import axios from 'axios';

// Configuration de l'icône du marqueur
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
});

const LocationMarker = ({ setPosition, setFormData }) => {
  const map = useMapEvents({
    async click(e) {
      setPosition(e.latlng);
      try {
        // Utiliser l'API de géocodage inverse pour obtenir l'adresse à partir des coordonnées
        const response = await axios.get(
          `https://nominatim.openstreetmap.org/reverse?format=json&lat=${e.latlng.lat}&lon=${e.latlng.lng}`
        );
        const address = response.data.display_name || `${e.latlng.lat.toFixed(4)}, ${e.latlng.lng.toFixed(4)}`;
        setFormData(prev => ({
          ...prev,
          adresse: address,
          position: e.latlng
        }));
      } catch (error) {
        console.error("Erreur de géocodage:", error);
        setFormData(prev => ({
          ...prev,
          adresse: `${e.latlng.lat.toFixed(4)}, ${e.latlng.lng.toFixed(4)}`,
          position: e.latlng
        }));
      }
    },
  });

  return null;
};

const UniversiteStats = () => {
  const [stats, setStats] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    nomUniversite: '',
    adresse: '',
    position: null
  });

  // Charger les statistiques initiales
  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      const response = await axios.get('http://localhost:8087/stats/adresse');
      const statsArray = Object.entries(response.data).map(([adresse, count]) => ({
        adresse,
        count: Math.floor(Number(count))
      }));
      setStats(statsArray);
    } catch (error) {
      console.error("Erreur:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8087/add-university', {
        nomUniversite: formData.nomUniversite,
        adresse: formData.adresse,
        latitude: formData.position?.lat,
        longitude: formData.position?.lng
      });
      fetchStats(); // Rafraîchir les stats
      setShowForm(false);
      setFormData({
        nomUniversite: '',
        adresse: '',
        position: null
      });
    } catch (error) {
      console.error("Erreur lors de l'ajout:", error);
    }
  };

  if (loading) return <div className="loading">Chargement...</div>;

  return (
    <div className="universite-container">
      <h2>Statistiques des Universités par Adresse</h2>
      
      <button 
        onClick={() => setShowForm(!showForm)}
        className="toggle-form-btn"
      >
        {showForm ? 'Masquer le formulaire' : 'Ajouter une université'}
      </button>

      {showForm && (
        <div className="form-container">
          <h3>Ajouter une nouvelle université</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Nom de l'université:</label>
              <input
                type="text"
                name="nomUniversite"
                value={formData.nomUniversite}
                onChange={handleInputChange}
                required
              />
            </div>
            
            <div className="form-group">
              <label>Adresse:</label>
              <input
                type="text"
                name="adresse"
                value={formData.adresse}
                onChange={handleInputChange}
                required
              />
              <small>Cliquez sur la carte pour détecter l'adresse ou saisissez-la manuellement</small>
            </div>
            
            <div className="map-container">
              <MapContainer
                center={[48.8566, 2.3522]} // Paris par défaut
                zoom={13}
                style={{ height: '300px', width: '100%' }}
              >
                <TileLayer
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                />
                <LocationMarker 
                  setPosition={(pos) => setFormData(prev => ({...prev, position: pos}))} 
                  setFormData={setFormData}
                />
                {formData.position && (
                  <Marker position={formData.position}>
                    <Popup>Nouvelle université</Popup>
                  </Marker>
                )}
              </MapContainer>
            </div>
            
            <button type="submit" className="submit-btn">
              Enregistrer
            </button>
          </form>
        </div>
      )}

      <div className="chart-container">
        <ResponsiveContainer width="100%" height={400}>
          <BarChart
            data={stats}
            margin={{
              top: 5,
              right: 30,
              left: 20,
              bottom: 100,
            }}
          >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis 
              dataKey="adresse" 
              angle={-45} 
              textAnchor="end"
              height={100}
              interval={0}
            />
            <YAxis 
              allowDecimals={false}
              tickCount={Math.max(...stats.map(item => item.count)) + 1}
            />
            <Tooltip formatter={(value) => [parseInt(value), 'Nombre']} />
            <Legend />
            <Bar 
              dataKey="count" 
              name="Nombre d'universités" 
              fill="#8884d8" 
              barSize={30}
            />
          </BarChart>
        </ResponsiveContainer>
      </div>

      <div className="table-container">
        <h3>Liste détaillée</h3>
        <div className="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Adresse</th>
                <th>Nombre d'universités</th>
              </tr>
            </thead>
            <tbody>
              {stats.map((item, index) => (
                <tr key={index}>
                  <td>{item.adresse}</td>
                  <td>{parseInt(item.count)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      <style jsx>{`
        .universite-container {
          max-width: 1200px;
          margin: 0 auto;
          padding: 20px;
        }
        .form-container {
          background: #f5f5f5;
          padding: 20px;
          border-radius: 8px;
          margin: 20px 0;
        }
        .form-group {
          margin-bottom: 15px;
        }
        .form-group label {
          display: block;
          margin-bottom: 5px;
          font-weight: bold;
        }
        .form-group input {
          width: 100%;
          padding: 8px;
          border: 1px solid #ddd;
          border-radius: 4px;
        }
        .form-group small {
          display: block;
          margin-top: 5px;
          color: #666;
          font-size: 0.8em;
        }
        .map-container {
          margin: 15px 0;
          border: 1px solid #ddd;
          border-radius: 4px;
          overflow: hidden;
        }
        .toggle-form-btn, .submit-btn {
          background: #4CAF50;
          color: white;
          border: none;
          padding: 10px 15px;
          border-radius: 4px;
          cursor: pointer;
          margin: 5px 0;
        }
        .toggle-form-btn:hover, .submit-btn:hover {
          background: #45a049;
        }
        .loading {
          text-align: center;
          padding: 50px;
          font-size: 1.2em;
        }
        .chart-container {
          margin: 40px 0;
        }
        .table-container {
          width: 100%;
          margin-top: 40px;
          display: flex;
          flex-direction: column;
          align-items: center;
        }
        .table-container h3 {
          text-align: center;
        }
        .table-wrapper {
          width: 100%;
          overflow-x: auto;
          margin-top: 20px;
        }
        table {
          width: 100%;
          border-collapse: collapse;
          margin: 0 auto;
        }
        th, td {
          padding: 12px;
          border: 1px solid #ddd;
          text-align: center;
        }
        th {
          background-color: #f2f2f2;
        }
        tr:nth-child(even) {
          background-color: #f9f9f9;
        }
      `}</style>
    </div>
  );
};

export default UniversiteStats;