FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

#docker build -t motoflow-server:1.0 .
#docker run -p 8080:8080 motoflow-server:1.0
#docker login
#docker tag motoflow-server:1.0 matheusesteves10/motoflow-server:1.0
#docker push matheusesteves10/motoflow-server:1.0