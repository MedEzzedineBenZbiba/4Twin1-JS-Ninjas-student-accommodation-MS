import axios from 'axios';

const API_BASE = 'http://localhost:8090/chambre';

export const chambreApi = {
    getAll: () => axios.get(`${API_BASE}/retrieve-all-chambres`),
    getById: (id) => axios.get(`${API_BASE}/retrieve-chambre/${id}`),
    add: (data) => axios.post(`${API_BASE}/add-chambre`, data),
    update: (data) => axios.put(`${API_BASE}/modify-chambre`, data),
    remove: (id) => axios.delete(`${API_BASE}/remove-chambre/${id}`),
    getByType: (type) => axios.get(`${API_BASE}/getChambreByTypeChambre/${type}`),
    getByNumber: (num) => axios.get(`${API_BASE}/getChambreByNum/${num}`)
};
