FROM openjdk:17-alpine AS build

WORKDIR /app

COPY . .

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=build /app/target/Agenda-Corespondenta-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
