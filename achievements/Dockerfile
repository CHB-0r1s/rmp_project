# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the build files to the container
COPY achievements/build.gradle.kts .
COPY achievements/settings.gradle.kts .
COPY achievements/gradle.properties .
COPY achievements/gradlew .
COPY achievements/gradlew.bat .
COPY achievements/gradle ./gradle

# Copy the source code to the container
COPY achievements/src ./src

# Build the application with Gradle
RUN ./gradlew build -x test --no-daemon -Dorg.gradle.jvmargs="--add-opens java.base/java.util=ALL-UNNAMED"

# Copy the built application from the build stage
#COPY --from=build /app/build/libs/auth-all.jar ./ktor-app.jar

# Expose the port that the application will run on
EXPOSE 5023

# Command to run the application
#CMD ["java", "-jar", "ktor-app.jar"]
RUN ls -l build/libs/

CMD ["java", "-jar", "build/libs/achievements-all.jar"]
