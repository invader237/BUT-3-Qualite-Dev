FROM mysql:8.0

# Copier les scripts d'initialisation dans le dossier d'entrypoint
COPY init.sql /docker-entrypoint-initdb.d/
COPY ./script/*.sql /docker-entrypoint-initdb.d/

# Variables locales
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

EXPOSE 3306
