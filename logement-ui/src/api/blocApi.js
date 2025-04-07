import axios from 'axios';

const API_BASE = 'http://localhost:8090/bloc';

export const blocApi = {
    getAll: () => axios.get(`${API_BASE}/retrieve-all-blocs`),
    getById: (id) => axios.get(`${API_BASE}/retrieve-bloc/${id}`),
    getWeather: (id) => axios.get(`${API_BASE}/${id}/meteo`),
    add: (data) => axios.post(`${API_BASE}/add-bloc`, data),
    update: (data) => axios.put(`${API_BASE}/modify-bloc`, data),
    remove: (id) => axios.delete(`${API_BASE}/remove-bloc/${id}`),
    getByCapaciteMin: (cap) => axios.get(`${API_BASE}/find-by-capacite-minimum/${cap}`)
};
