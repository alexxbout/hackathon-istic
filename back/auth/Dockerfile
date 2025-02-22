# Étape de construction avec Maven et JDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /auth
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape d'exécution avec JDK 21
FROM openjdk:21-jdk-slim
WORKDIR /auth
COPY --from=build /auth/target/*.jar auth.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "auth.jar"]