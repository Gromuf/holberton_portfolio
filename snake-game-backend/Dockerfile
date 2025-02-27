FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# ✅ Donner les permissions d'exécution au script mvnw
RUN chmod +x mvnw

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
