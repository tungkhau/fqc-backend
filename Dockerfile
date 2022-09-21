FROM openjdk:11.0.11-jre
COPY target/*.jar app.jar
ENTRYPOINT exec java -jar /app.jar --spring.profiles.active=docker