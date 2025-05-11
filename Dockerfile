# Fase di build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
# Dà i permessi di esecuzione a mvnw e poi esegui
RUN chmod +x mvnw && ./mvnw clean package -DskipTests -Pproduction

# Fase di runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Esponi la porta (Render la sovrascriverà con la sua variabile PORT)
EXPOSE 10000

# Avvia l'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]