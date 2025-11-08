FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY build/libs/service-discovery-*.jar service-discovery.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "service-discovery.jar"]