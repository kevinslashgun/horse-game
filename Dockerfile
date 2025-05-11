FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Espone la porta che Render si aspetta
EXPOSE 8080

# Avvia l'app con la porta fornita da Render
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
