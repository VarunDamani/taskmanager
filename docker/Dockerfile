# Stage 1: Build the app
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Gradle wrapper and dependencies first for caching
COPY ../gradlew .
COPY ../gradle gradle
COPY ../build.gradle settings.gradle ./

RUN ./gradlew --no-daemon build -x test

# Copy the rest of the source code
COPY ../src src

RUN ./gradlew --no-daemon bootJar

# Stage 2: Run the app
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]