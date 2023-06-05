FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /APP
COPY --from=build ./app/target/*.jar ./app.jar
ARG EUREKA_SERVER=localhost
ENTRYPOINT java -jar -Dspring.profiles.active=prod app.jar