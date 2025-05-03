FROM node:23-alpine AS build

# Ustaw katalog roboczy
WORKDIR /criti-cloud

# Skopiuj package.json oraz package-lock.json
COPY package*.json ./

# Zainstaluj zależności
RUN npm install

# Skopiuj resztę plików aplikacji do kontenera
COPY . .

# Zbuduj aplikację (na przykład za pomocą Vite)
RUN npm run build

# Ustaw port, na którym aplikacja będzie dostępna
EXPOSE 3000

# Uruchom aplikację
CMD ["npm", "run", "dev"]