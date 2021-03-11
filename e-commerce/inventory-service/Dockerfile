FROM openjdk:8-jdk-alpine
EXPOSE 8081
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-DDB_URL=${ENV_DB_URL}", "-jar" ,"app.jar"]