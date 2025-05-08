# ─────────────── build stage ───────────────
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# copy Gradle wrapper & build files
COPY user_goals/build.gradle.kts .
COPY user_goals/settings.gradle.kts .
COPY user_goals/gradle.properties .
COPY user_goals/gradlew .
COPY user_goals/gradlew.bat .
COPY user_goals/gradle ./gradle

# copy sources
COPY user_goals/src ./src

# build fat jar (<module>-all.jar is created automatically by io.ktor.plugin)
RUN ./gradlew build --no-daemon

# ─────────────── run stage ────────────────
FROM openjdk:17-jdk-slim
WORKDIR /app
# copy the fat jar produced above
COPY --from=build /app/build/libs/user_goals-all.jar ./ktor-app.jar
EXPOSE 5015
CMD ["java", "-jar", "ktor-app.jar"]
