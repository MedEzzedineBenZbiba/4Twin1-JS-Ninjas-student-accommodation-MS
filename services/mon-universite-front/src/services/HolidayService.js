import axios from 'axios';

const HOLIDAY_API_URL = 'http://localhost:8222/holidays';

export const getHolidays = (year, countryCode) => {
  return axios.get(`${HOLIDAY_API_URL}/${year}/${countryCode}`);
};