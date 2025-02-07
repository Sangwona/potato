# Stage 1: Build the JAR inside the container
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy the entire project
COPY . .

# Build the JAR file using Gradle
RUN ./gradlew clean build --no-daemon

# Stage 2: Create a new container to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]