import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import axios from 'axios';

// Icone Leaflet
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
});

const LocationMarker = ({ setPosition, setFormData }) => {
  useMapEvents({
    async click(e) {
      setPosition(e.latlng);
      try {
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

  const token = localStorage.getItem("token"); // Récupération du token

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      const response = await axios.get('http://localhost:8090/university/stats/adresse', {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

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
      await axios.post('http://localhost:8090/university', {
        nomUniversite: formData.nomUniversite,
        adresse: formData.adresse,
        latitude: formData.position?.lat,
        longitude: formData.position?.lng
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      fetchStats();
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

      <button onClick={() => setShowForm(!showForm)} className="toggle-form-btn">
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
                center={[36.862499, 10.195556]}
                zoom={13}
                style={{ height: '300px', width: '100%' }}
              >
                <TileLayer
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  attribution='&copy; OpenStreetMap'
                />
                <LocationMarker setPosition={(pos) => setFormData(prev => ({ ...prev, position: pos }))} setFormData={setFormData} />
                {formData.position && (
                  <Marker position={formData.position}>
                    <Popup>Nouvelle université</Popup>
                  </Marker>
                )}
              </MapContainer>
            </div>

            <button type="submit" className="submit-btn">Enregistrer</button>
          </form>
        </div>
      )}

      <div className="chart-container">
        <ResponsiveContainer width="100%" height={400}>
          <BarChart data={stats} margin={{ top: 5, right: 30, left: 20, bottom: 100 }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="adresse" angle={-45} textAnchor="end" height={100} interval={0} />
            <YAxis allowDecimals={false} tickCount={Math.max(...stats.map(item => item.count)) + 1} />
            <Tooltip formatter={(value) => [parseInt(value), 'Nombre']} />
            <Legend />
            <Bar dataKey="count" name="Nombre d'universités" fill="#8884d8" barSize={30} />
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
    </div>
  );
};

export default UniversiteStats;
``
