# Étape 1 : Build avec Maven et Java 21
FROM maven:3.9.9-eclipse-temurin-24-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution avec Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-Dserver.port=8888", "-jar", "app.jar"]