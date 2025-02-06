# 1. JDK 17 기반의 이미지 사용
FROM registry.hub.docker.com/library/openjdk:17-jdk-slim

# 2. 컨테이너 내에서 작업할 디렉토리 생성
WORKDIR /app

# 3. 현재 디렉토리에서 JAR 파일을 컨테이너 내부로 복사
COPY build/libs/*.jar app.jar

# 4. 컨테이너에서 사용할 포트 설정 (Spring Boot 기본 8080)
EXPOSE 8080

# 5. 컨테이너 실행 시 Java 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]