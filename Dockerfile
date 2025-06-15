# Dockerfile
FROM openjdk:17-jdk AS build
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM openjdk:17-jdk
WORKDIR /
COPY --from=build /app/target/*.jar app.jar
# Opcional: expone para depuración local
EXPOSE 8080

ENTRYPOINT ["sh","-c","java -jar /app.jar --server.port=$PORT"]