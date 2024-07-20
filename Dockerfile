FROM gradle:8-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# https://docs.gradle.org/2.4/userguide/gradle_daemon.html
RUN gradle buildFatJar --no-daemon

FROM openjdk:17
EXPOSE 18080:18080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
