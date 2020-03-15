# Docker file for Registration microservice 
FROM openjdk:8-jre-alpine

LABEL maintainer = "bdave" 
LABEL "version" = "1.0" 

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} registration.jar
EXPOSE 8080:8080

ENTRYPOINT ["java","-jar","/registration.jar"]