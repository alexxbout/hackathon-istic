#!/bin/sh

echo "Waiting for backend to be ready..."
sleep 15

echo "Vérification de l'initialisation..."
INIT_CHECK=$(PGPASSWORD=postgres psql -h postgres -U postgres -d skillstasks -tAc "SELECT to_regclass('public.initialization_done');")

# Nettoyage du résultat pour éviter les espaces
INIT_CHECK=$(echo "$INIT_CHECK" | xargs)

echo "Résultat de la vérification: '$INIT_CHECK'"

if [ "$INIT_CHECK" = "initialization_done" ]; then
  echo "Initialisation déjà effectuée, passage."
else
  echo "Insertion des données initiales..."
  PGPASSWORD=postgres psql -h postgres -U postgres -d skillstasks -f /docker-entrypoint-initdb.d/main.sql

  echo "Données insérées avec succès."
  echo "Création de la table marqueur..."
  PGPASSWORD=postgres psql -h postgres -U postgres -d skillstasks -c "CREATE TABLE initialization_done (initialized boolean);"
  PGPASSWORD=postgres psql -h postgres -U postgres -d skillstasks -c "INSERT INTO initialization_done VALUES (true);"

  echo "Initialisation terminée."
fi