# Etapa de construcción usando Gradle y JDK 17
FROM gradle:8.8.0-jdk17 AS build

WORKDIR /app

COPY sistemaedu/build.gradle sistemaedu/settings.gradle /app/

COPY sistemaedu/src /app/src

# Ejecuta la construcción con Gradle
RUN gradle assemble --no-daemon

# Etapa de ejecución usando JDK 17
FROM openjdk:17-jdk-slim

WORKDIR /app

# Instalar sudo (si es necesario) y dependencias adicionales
RUN apt-get update && apt-get install -y sudo

# Copia el archivo .jar de la etapa de construcción
COPY --from=build /app/build/libs/sistemaedu-0.0.1-SNAPSHOT.jar ./app.jar

# Exponer puertos si es necesario (por ejemplo, para un servicio HTTP)
EXPOSE 8080

# Configurar el comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
