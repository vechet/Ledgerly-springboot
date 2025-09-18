# Dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar ledgerly.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ledgerly.jar"]
