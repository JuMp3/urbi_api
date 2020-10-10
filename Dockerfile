FROM fra.ocir.io/sisalspa/olg/promoloyalty/openjdk-alpine-slim:release-java-11
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]