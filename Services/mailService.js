const nodemailer = require('nodemailer');

const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'nourboua20@gmail.com',
    pass: 'stoc sedm nzbm gutk'
  }
});

const sendMail = async (to, subject, body) => {
  const mailOptions = {
    from: 'nourboua20@gmail.com',
    to,
    subject,
    text: body
  };

  await transporter.sendMail(mailOptions);
};

module.exports = { sendMail };
