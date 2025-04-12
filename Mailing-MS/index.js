const express = require('express');
const bodyParser = require('body-parser');
const connectDB = require('./config/db');
const emailRoutes = require('./routes/emailRoutes');


const app = express();
const PORT = 4000;

connectDB();


app.use(bodyParser.json());
app.use('/api/emails', emailRoutes);

app.listen(PORT, () => {
  console.log(`Mailing service running on http://localhost:${PORT}`);
});
