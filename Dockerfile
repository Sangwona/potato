## Stage 1: Build the JAR inside the container
#FROM openjdk:17-jdk-slim AS build
#
#WORKDIR /app
#
## Render에서 설정한 환경변수를 Docker 빌드 단계에서도 사용하도록 ARG 선언
#ARG DB_URL
#ARG DB_USERNAME
#ARG DB_PASSWORD
#
## 환경변수를 설정
#ENV DB_URL=${DB_URL}
#ENV DB_USERNAME=${DB_USERNAME}
#ENV DB_PASSWORD=${DB_PASSWORD}
#
## Copy the entire project
#COPY . .
#
## Build the JAR file using Gradle
#RUN ./gradlew clean build --no-daemon
#
## Stage 2: Create a new container to run the application
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#
## Copy the built JAR from the previous stage
#COPY --from=build /app/build/libs/*.jar app.jar
#
## 실행 시 환경변수 유지
#ENV DB_URL=${DB_URL}
#ENV DB_USERNAME=${DB_USERNAME}
#ENV DB_PASSWORD=${DB_PASSWORD}
#
## Expose port 8080
#EXPOSE 8080
#
## Run the application
#CMD ["java", "-jar", "app.jar"]

#---------------------------
#
# Stage 1: Build
#FROM openjdk:17-jdk-slim AS build
#
#WORKDIR /app
#
## 환경변수를 ARG로 선언
#ARG DB_URL
#ARG DB_USERNAME
#ARG DB_PASSWORD
#
## 환경변수를 ENV로 설정 (빌드 단계에서 사용)
#ENV DB_URL=${DB_URL}
#ENV DB_USERNAME=${DB_USERNAME}
#ENV DB_PASSWORD=${DB_PASSWORD}
#
## 프로젝트 복사 및 빌드
#COPY . .
#RUN ./gradlew clean build -x test --no-daemon
#
## Stage 2: Run
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#
## 빌드된 JAR 복사
#COPY --from=build /app/build/libs/*.jar app.jar
#
## 런타임 환경변수 설정 (빌드 시 전달된 ARG 값을 사용하지 않음)
#ENV DATABASE_URL=${DB_URL}
#ENV DATABASE_USER=${DB_USER}
#ENV DATABASE_PASSWORD=${DB_PASSWORD}
#
## 애플리케이션 실행
#CMD ["sh", "-c", "java -Dspring.datasource.url=$DATABASE_URL \
#                  -Dspring.datasource.username=$DATABASE_USER \
#                  -Dspring.datasource.password=$DATABASE_PASSWORD \
#                  -jar app.jar"]

#============================================

# Use the official Gradle image to build the JAR
# Use the official Gradle image to build the JAR
# Use the official Gradle image to build the JAR
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