FROM openjdk:17-alpine AS build

WORKDIR /app

COPY build.gradle /app/
COPY gradlew /app/
COPY gradle /app/gradle

COPY src /app/src

RUN chmod +x ./gradlew

RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-alpine AS runner

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]