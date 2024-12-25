FROM eclipse-temurin:23.0.1_11-jdk-alpine

EXPOSE 5005 8080
COPY entrypoint.sh /app/entrypoint.sh

RUN chmod +x /app/entrypoint.sh && \
    apk update && \
    apk add --no-cache bash curl maven


WORKDIR /app

COPY .mvn .mvn
COPY mvnw.cmd mvnw.cmd
COPY mvnw mvnw
COPY pom.xml pom.xml
COPY .m2/ /root/.m2/
COPY ./src ./src

ENTRYPOINT ["/app/entrypoint.sh"]
