# Stage 1: build
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: runtime
FROM openjdk:17-jdk
WORKDIR /
# Copiamos el WAR repackaged y lo renombramos a app.jar para mantener java -jar /app.jar
COPY --from=build /app/target/*.war app.war
ENTRYPOINT ["java", "-jar", "/app.war"]