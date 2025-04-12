import React, { useState, useEffect } from 'react';
import { Form, Table, Alert, Spinner } from 'react-bootstrap';
import { getHolidays } from '../services/HolidayService';

const MONTHS = [
  { label: 'Janvier', num: 1 }, { label: 'Février', num: 2 },
  { label: 'Mars', num: 3 }, { label: 'Avril', num: 4 },
  { label: 'Mai', num: 5 }, { label: 'Juin', num: 6 },
  { label: 'Juillet', num: 7 }, { label: 'Août', num: 8 },
  { label: 'Septembre', num: 9 }, { label: 'Octobre', num: 10 },
  { label: 'Novembre', num: 11 }, { label: 'Décembre', num: 12 },
];

const COUNTRIES = [
  { code: 'TN', label: 'Tunisie 🇹🇳' },
  { code: 'FR', label: 'France 🇫🇷' },
  { code: 'US', label: 'États-Unis 🇺🇸' },
];

function HolidayList() {
  const [year] = useState(new Date().getFullYear());
  const [countryCode, setCountryCode] = useState('TN');
  const [allHolidays, setAllHolidays] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedMonth, setSelectedMonth] = useState('');

  // charger les jours fériés
  useEffect(() => {
    if (!countryCode) return;
    setLoading(true);
    setFiltered([]); // reset filtered lors du changement
    getHolidays(year, countryCode)
      .then(res => setAllHolidays(res.data))
      .catch(() => alert("Impossible de charger les jours fériés."))
      .finally(() => setLoading(false));
  }, [year, countryCode]);

  const handleChangeMonth = (e) => {
    const monthNum = parseInt(e.target.value, 10);
    setSelectedMonth(monthNum);
    const list = allHolidays.filter(h => {
      const m = new Date(h.date).getMonth() + 1;
      return m === monthNum;
    });
    setFiltered(list);
  };

  const handleChangeCountry = (e) => {
    setCountryCode(e.target.value);
    setSelectedMonth('');
    setFiltered([]);
  };

  return (
    <div>
      <h2>Jours Fériés {year}</h2>

      {loading && <Spinner animation="border" />}

      <Form.Group className="mb-3" controlId="countrySelect">
        <Form.Label>Sélectionnez un pays</Form.Label>
        <Form.Select value={countryCode} onChange={handleChangeCountry}>
          {COUNTRIES.map(c => (
            <option key={c.code} value={c.code}>{c.label}</option>
          ))}
        </Form.Select>
      </Form.Group>

      <Form.Group className="mb-3" controlId="monthSelect">
        <Form.Label>Sélectionnez un mois</Form.Label>
        <Form.Select value={selectedMonth} onChange={handleChangeMonth}>
          <option value="">-- Choisir --</option>
          {MONTHS.map(m => (
            <option key={m.num} value={m.num}>{m.label}</option>
          ))}
        </Form.Select>
      </Form.Group>

      {selectedMonth && (
        filtered.length > 0
          ? (
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Nom Local</th>
                </tr>
              </thead>
              <tbody>
                {filtered.map((h, i) => (
                  <tr key={i}>
                    <td>{h.date}</td>
                    <td>{h.name}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          )
          : (
            <Alert variant="danger">
              Sérieusement ? Aucun jour férié en <strong>{MONTHS.find(m => m.num === selectedMonth)?.label}</strong> pour <strong>{countryCode}</strong> !  
              T’as vraiment pas de chance… 😤
            </Alert>
          )
      )}
    </div>
  );
}

export default HolidayList;