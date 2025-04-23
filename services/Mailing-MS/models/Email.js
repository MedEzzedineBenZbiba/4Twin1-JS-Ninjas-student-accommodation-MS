const mongoose = require('mongoose');

const EmailSchema = new mongoose.Schema({
  to: String,
  subject: String,
  body: String,
  sentAt: {
    type: Date,
    default: Date.now
  }
});

module.exports = mongoose.model('Email', EmailSchema);
