# Fase 1: Construcción de la app
FROM openjdk:17-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Fase 2: Imagen final
FROM openjdk:17-jdk
VOLUME /tmp
COPY --from=build /app/target/final-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar /app.jar --server.port=$PORT"]