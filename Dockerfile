# Fase di build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN chmod +x mvnw && \
    ./mvnw clean package -DskipTests -Pproduction

# Fase di runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Esponi la porta predefinita
EXPOSE 10000

# Forza il binding su 0.0.0.0 e usa la variabile PORT
CMD ["sh", "-c", "java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar app.jar"]