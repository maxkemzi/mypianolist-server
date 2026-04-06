FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# copy pom first (better caching)
COPY pom.xml .
COPY src ./src

# build jar (skip tests optional)
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine as runner

WORKDIR /app

# copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# expose Spring Boot port
EXPOSE 8080

# run app
ENTRYPOINT ["java", "-jar", "app.jar"]
