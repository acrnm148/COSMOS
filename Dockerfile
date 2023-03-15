FROM adoptopenjdk/openjdk8 AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

# 실제 컨테이너로 생성될 이미지를 생성하는 부분
FROM adoptopenjdk/openjdk8
COPY --from=builder build/libs/*.jar app.jar

EXPOSE 8081

# jar 파일 
ENTRYPOINT ["java", "-jar", "/app.jar"]
