FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY build/libs/user-service.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
