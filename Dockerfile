FROM gcr.io/distroless/java:11
#FROM adoptopenjdk/openjdk11:alpine-slim
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]