# Étape 1 : Utilise une image Node.js officielle (ici Node 16)
FROM node:18

# Étape 2 : Définit le répertoire de travail dans le conteneur
WORKDIR /usr/src/app/mailing-back

# Étape 3 : Copie les fichiers de dépendances dans l'image
COPY package*.json ./

# Étape 4 : Installe les dépendances
RUN npm install

# Étape 5 : Copie l'intégralité du code source dans le conteneur
COPY . .

# Étape 6 : Expose le port sur lequel ton backend écoute (ici 4000)
EXPOSE 4000

# Étape 7 : Démarre ton application
CMD ["npm", "start"]
