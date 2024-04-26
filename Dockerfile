FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

# Copy only the pom.xml to leverage Docker cache layer
COPY pom.xml .
# Download dependencies and build project
RUN mvn -B dependency:resolve dependency:resolve-plugins

# Copy the entire project and build it
COPY . .
RUN mvn -B clean install

FROM openjdk:17-alpine

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/Agenda-Corespondenta-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
