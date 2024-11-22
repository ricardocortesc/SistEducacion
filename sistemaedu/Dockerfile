FROM gradle:8.8.0-jdk17 AS build

WORKDIR /app

# Copiar los archivos necesarios para construir el proyecto
COPY sistemaedu/build.gradle sistemaedu/settings.gradle /app/
COPY sistemaedu/src /app/src

# Construir el JAR de la aplicación
RUN gradle assemble --no-daemon

# Etapa final: Crear un contenedor con la aplicación y RabbitMQ
FROM openjdk:17-jdk-slim

WORKDIR /app

# Instalar RabbitMQ y sus dependencias
RUN apt-get update && \
    apt-get install -y sudo curl gnupg && \
    curl -fsSL https://packages.erlang-solutions.com/ubuntu/erlang_solutions.asc | sudo apt-key add - && \
    echo "deb https://packages.erlang-solutions.com/ubuntu focal contrib" | sudo tee /etc/apt/sources.list.d/erlang.list && \
    apt-get update && \
    apt-get install -y erlang rabbitmq-server && \
    apt-get clean

# Habilitar el plugin de administración de RabbitMQ
RUN rabbitmq-plugins enable rabbitmq_management

# Copiar el JAR de la aplicación desde la etapa de construcción
COPY --from=build /app/build/libs/sistemaedu-0.0.1-SNAPSHOT.jar ./app.jar

# Exponer los puertos necesarios
EXPOSE 80 5672 15672

# Iniciar RabbitMQ y luego la aplicación
CMD service rabbitmq-server start && \
    java -jar app.jar
