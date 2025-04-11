import React, { useState, useEffect } from 'react';
import sendEmail from '../services/emailService';
import { fetchEmails } from '../services/EtudiantService'; // 👈
import 'bootstrap/dist/css/bootstrap.min.css';

const MailSender = () => {
  const [to, setTo] = useState('');
  const [subject, setSubject] = useState('');
  const [body, setBody] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [emailOptions, setEmailOptions] = useState([]);
  const [filteredEmails, setFilteredEmails] = useState([]);

  useEffect(() => {
    const loadEmails = async () => {
      try {
        const emails = await fetchEmails();
        setEmailOptions(emails);
      } catch (err) {
        console.error('Erreur lors du chargement des emails :', err);
      }
    };

    loadEmails();
  }, []);

  // Filtrage des emails en fonction de ce que l'utilisateur tape
  const handleInputChange = (e) => {
    const value = e.target.value;
    setTo(value);
  
    // Vérifier que la valeur n'est pas null ou vide avant de procéder
    if (value) {
      const filtered = emailOptions.filter((email) => 
        email && email.toLowerCase().includes(value.toLowerCase()) // Vérification si 'email' n'est pas null ou undefined
      );
      setFilteredEmails(filtered);
    } else {
      setFilteredEmails([]); // Si la saisie est vide, vider la liste des suggestions
    }
  };
  const handleSendEmail = async () => {
    setLoading(true);
    try {
      const data = await sendEmail({ to, subject, body });
      setMessage({ type: 'success', text: data.message });
      setTo('');
      setSubject('');
      setBody('');
    } catch (error) {
      if (error.response) {
        setMessage({ type: 'danger', text: `Erreur serveur : ${error.response.data.error}` });
      } else {
        setMessage({ type: 'danger', text: `Erreur réseau : ${error.message}` });
      }
    } finally {
      setLoading(false);
    }
  };

  const handleEmailSelection = (email) => {
    setTo(email);
    setFilteredEmails([]); // Fermer la liste une fois qu'on a sélectionné un email
  };

  return (
    <div className="container py-5">
      <div className="card shadow-lg rounded-4 p-4 mx-auto" style={{ maxWidth: '600px' }}>
        <h2 className="mb-4 text-center text-primary">📧 Envoyer un Email</h2>

        <div className="mb-3">
          <label className="form-label">Destinataire</label>
          <input
            type="email"
            className="form-control"
            value={to}
            onChange={handleInputChange}
            placeholder="exemple@email.com"
          />
          {filteredEmails.length > 0 && (
            <ul className="list-group mt-2" style={{ maxHeight: '150px', overflowY: 'auto' }}>
              {filteredEmails.map((email, index) => (
                <li
                  key={index}
                  className="list-group-item list-group-item-action"
                  style={{ cursor: 'pointer' }}
                  onClick={() => handleEmailSelection(email)}
                >
                  {email}
                </li>
              ))}
            </ul>
          )}
        </div>

        <div className="mb-3">
          <label className="form-label">Objet</label>
          <input
            type="text"
            className="form-control"
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
            placeholder="Sujet de l'email"
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Message</label>
          <textarea
            className="form-control"
            rows="6"
            value={body}
            onChange={(e) => setBody(e.target.value)}
            placeholder="Votre message ici..."
          />
        </div>

        <button
          className="btn btn-primary w-100"
          onClick={handleSendEmail}
          disabled={loading}
        >
          {loading ? 'Envoi en cours...' : 'Envoyer'}
        </button>

        {message && (
          <div className={`alert alert-${message.type} mt-4`} role="alert">
            {message.text}
          </div>
        )}
      </div>
    </div>
  );
};

export default MailSender;
