FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app

# Copy only Gradle wrapper & build files first (to cache dependencies)
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Give Gradle Wrapper execute permissions
RUN chmod +x gradlew

# Download dependencies (this step is cached)
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the application code
COPY . .

# Build the JAR inside Docker (Skipping Tests)
RUN ./gradlew build --no-daemon -x test

# Use a smaller OpenJDK image for the final application
FROM openjdk:17
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar ecard.jar

# Set environment variables (will be overridden by Render)
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# Run the application
ENTRYPOINT ["java", "-jar", "ecard.jar"]