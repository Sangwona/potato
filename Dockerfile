# Stage 1: Build the JAR inside the container
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Render에서 설정한 환경변수를 Docker 빌드 단계에서도 사용하도록 ARG 선언
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

# 환경변수를 설정
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# Copy the entire project
COPY . .

# Build the JAR file using Gradle
RUN ./gradlew clean build --no-daemon

# Stage 2: Create a new container to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# 실행 시 환경변수 유지
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]