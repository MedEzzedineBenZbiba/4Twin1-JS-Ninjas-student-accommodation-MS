// services/emailService.js
import axios from 'axios';

const sendEmail = async ({ to, subject, body }) => {
  const response = await axios.post('http://localhost:8090/mailing/api/emails/send', {
    to,
    subject,
    body,
  });
  return response.data;
};


export default sendEmail;
export const API_URL = process.env.REACT_APP_API_URL || '/etudiant';
