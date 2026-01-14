FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /home/gradle/project

COPY gradle/ gradle/
COPY gradlew .
COPY build.gradle settings.gradle gradle.properties* ./

COPY src ./src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]