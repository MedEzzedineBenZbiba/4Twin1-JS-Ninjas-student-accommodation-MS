const mongoose = require('mongoose');

const connectDB = async () => {
  try {
    // Utilise la variable d'environnement MONGO_URI sinon une valeur par défaut
    const mongoURI = process.env.MONGO_URI || 'mongodb://mongodb:27017/mailing';
    await mongoose.connect(mongoURI, {
      useNewUrlParser: true,
      useUnifiedTopology: true,
    });
    console.log('MongoDB connected');
  } catch (err) {
    console.error('Erreur de connexion à MongoDB :', err.message);
    process.exit(1);
  }
};

module.exports = connectDB;
