# Usa una JDK compatibile
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Seconda fase: immagine leggera
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Render usa la variabile PORT per comunicare
ENV PORT 8080
EXPOSE 8080

# Lancia Spring Boot e forza l'uso di PORT
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT} --server.address=0.0.0.0"]
