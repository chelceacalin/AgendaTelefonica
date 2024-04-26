FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

RUN ls target

COPY target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
