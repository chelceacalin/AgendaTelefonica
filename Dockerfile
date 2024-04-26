FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven
COPY . /workspace
WORKDIR /workspace

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=build /workspace/target/agenda-corespondenta-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
