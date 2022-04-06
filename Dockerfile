FROM maven:3.8.5-openjdk-11-slim as builder

WORKDIR /app

COPY . .

RUN --mount=type=cache,target=/root/.m2 mvn package dependency:tree -DskipTests=true

FROM adoptopenjdk/openjdk11:alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
ARG JAR_FILE=app/service/target/*.jar
COPY --from=builder ${JAR_FILE} bin/app.jar
ENTRYPOINT ["java","-jar","bin/app.jar"]