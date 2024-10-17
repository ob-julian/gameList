# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# Copy the Maven wrapper and set permissions
COPY .mvn/ .mvn
COPY mvnw .
RUN chmod +x mvnw

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM amazoncorretto:17-alpine3.20-jdk

WORKDIR /app

# Copy the executable JAR file from the build stage
COPY --from=build /app/target/game-list-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
