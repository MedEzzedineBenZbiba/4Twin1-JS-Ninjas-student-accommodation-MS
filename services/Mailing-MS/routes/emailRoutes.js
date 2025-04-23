const express = require('express');
const router = express.Router();
const Email = require('../models/Email');
const { sendMail } = require('../Services/mailService');

router.post('/send', async (req, res) => {
  const { to, subject, body } = req.body;

  try {
    await sendMail(to, subject, body);
    const email = new Email({ to, subject, body });
    await email.save();
    res.status(200).json({ message: 'Email envoyé et sauvegardé.' });
  } catch (err) {
    res.status(500).json({ error: 'Échec de l\'envoi', details: err.message });
  }
});

module.exports = router;
