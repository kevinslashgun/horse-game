# Fase di build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
# Costruisci in modalità produzione e crea un JAR eseguibile
RUN ./