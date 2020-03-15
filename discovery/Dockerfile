FROM java:8 
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} discovery-service.jar
EXPOSE 8761:8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/discovery-service.jar"]