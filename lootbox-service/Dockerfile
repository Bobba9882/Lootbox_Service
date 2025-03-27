# Stage 1: Build stage met Maven
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Alleen pom.xml kopiëren en dependencies installeren voor cache
COPY pom.xml .
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline

# Daarna source code toevoegen
COPY src ./src

# Build zonder tests
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage met kleinere JRE image
FROM eclipse-temurin:17-jre

WORKDIR /app

# Kopieer alleen de jar vanuit de vorige stage
COPY --from=builder /app/target/*.jar app.jar

# Expose de juiste poort
EXPOSE 8081

# Start de app
ENTRYPOINT ["java", "-jar", "app.jar"]
