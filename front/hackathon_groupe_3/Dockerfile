# Étape 1 : Builder l'application Nuxt
FROM node:18-alpine AS builder
WORKDIR /app

# Copier les fichiers de configuration et installer les dépendances
COPY package*.json ./
RUN npm install

# Copier le reste du code source
COPY . .

# Build de l'application
RUN npm run build

# Étape 2 : Image de production
FROM node:18-alpine AS runner
WORKDIR /app
ENV NODE_ENV=production

# Copier le build depuis l'étape builder
COPY --from=builder /app/.output ./.output
# (Optionnel) Copier package.json si nécessaire pour des modules dynamiques
COPY --from=builder /app/package*.json ./

# Exposer le port sur lequel votre application écoute (par défaut 3000)
EXPOSE 3000

# Lancer le serveur Nuxt en production
CMD ["node", ".output/server/index.mjs"]