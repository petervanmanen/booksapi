FROM maven:3.8.5-eclipse-temurin-17-alpine as topbooks
WORKDIR /workspace/app

COPY mvnw .
COPY pom.xml .
COPY src src
EXPOSE 8080

RUN mvn test
CMD mvn spring-boot:run