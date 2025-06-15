# Importar JDK y copiar los archivos requeridos
FROM openjdk:17-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copiar el wrapper de Maven
COPY mvnw .
COPY .mvn .mvn

# Establecer permisos de ejecución para el wrapper de Maven
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Fase 2: Crear la imagen final de Docker
FROM openjdk:17-jdk
VOLUME /tmp

# Copiar el JAR desde la etapa de compilación
COPY --from=build /app/target/final-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080