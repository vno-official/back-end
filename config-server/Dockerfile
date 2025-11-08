FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY build/libs/config-server.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
