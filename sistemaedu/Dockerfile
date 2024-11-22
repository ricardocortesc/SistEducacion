FROM gradle:8.8.0-jdk17 AS build

WORKDIR /app

COPY sistemaedu/build.gradle sistemaedu/settings.gradle /app/

COPY sistemaedu/src /app/src

RUN gradle assemble --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y sudo

COPY --from=build /app/build/libs/sistemaedu-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 80

ENTRYPOINT ["java" ,"-jar", "app.jar"]
