# Etap 1: Budowanie aplikacji
FROM openjdk:8-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etap 2: Budowanie obrazu
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Uruchom aplikację Spring Boot
CMD ["java", "-jar", "app.jar"]
