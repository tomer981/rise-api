#!/bin/bash
# entrypoint.sh

# Ensure Maven dependencies are offline
./mvnw dependency:go-offline

# Run the Spring Boot application in debug mode
exec ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n
