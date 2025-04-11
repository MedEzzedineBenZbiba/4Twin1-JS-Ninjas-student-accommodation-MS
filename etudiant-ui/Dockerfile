# Étape 1 : Construire l'application React
FROM node:18 AS build

# Répertoire de travail dans l'image
WORKDIR /app

# Copier le package.json et package-lock.json pour installer les dépendances
COPY package.json package-lock.json ./

# Installer les dépendances
RUN npm install

# Copier le reste du code source
COPY . .

# Construire l'application React pour la production
RUN npm run build

# Étape 2 : Créer une image Nginx pour servir l'application
FROM nginx:alpine

# Copier l'application construite dans le répertoire Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Exposer le port 80 pour accéder à l'application
EXPOSE 80

# Lancer Nginx pour servir l'application
CMD ["nginx", "-g", "daemon off;"]
