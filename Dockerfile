FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=build ./admin-api/target/admin-api-0.0.1-SNAPSHOT.jar adminapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "adminapi.jar"]

FROM openjdk:17.0.1-jdk-slim
COPY --from=build ./bot-api/target/bot-api-0.0.1-SNAPSHOT.jar botapi.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "botapi.jar"]