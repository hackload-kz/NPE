# Сборка приложения
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Запуск приложения
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar biletter.jar
ENTRYPOINT ["java", "-jar", "biletter.jar"]
