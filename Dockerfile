# Using a official OpenJDK runtime as parent image
FROM openjdk:17-jdk-alpine3.13

WORKDIR /app

# Copy the executable JAR file from the target directory to the container
COPY target/game-list-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]