FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY target/Agenda-Corespondenta-0.0.1-SNAPSHOT.jar /app/

CMD ["java", "-jar", "getting-started-with-springboot-and-docker-0.0.1-SNAPSHOT.jar"]