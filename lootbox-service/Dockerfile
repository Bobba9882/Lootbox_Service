# Gebruik een officiële Java 17 JDK als basisimage
FROM eclipse-temurin:17-jdk

# Stel de werkdirectory in
WORKDIR /app

# Kopieer de Maven dependencies (gebruikt voor efficiënte caching)
COPY pom.xml .
COPY src ./src

# Installeer Maven
RUN apt-get update && apt-get install -y maven

# Bouw de applicatie
RUN mvn clean package -DskipTests

# Gebruik een kleinere JRE image om de uiteindelijke container te bouwen
FROM eclipse-temurin:17-jre

# Stel de werkdirectory in
WORKDIR /app

# Kopieer de gegenereerde JAR uit de vorige stap
COPY --from=0 /app/target/*.jar app.jar

# Expose de juiste poort
EXPOSE 8080

# Start de applicatie
CMD ["java", "-jar", "app.jar"]
