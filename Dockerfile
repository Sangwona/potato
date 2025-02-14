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

# Stage 1: Build
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# 환경변수를 ARG로 선언
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

# 환경변수를 ENV로 설정 (빌드 단계에서 사용)
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# 프로젝트 복사 및 빌드
COPY . .
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Run
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 런타임 환경변수 설정 (빌드 시 전달된 ARG 값을 사용하지 않음)
ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

# 애플리케이션 실행
CMD ["sh", "-c", "java -Dspring.datasource.url=$DATABASE_URL \
                  -Dspring.datasource.username=$DATABASE_USER \
                  -Dspring.datasource.password=$DATABASE_PASSWORD \
                  -jar app.jar"]