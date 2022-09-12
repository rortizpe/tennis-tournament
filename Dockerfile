FROM openjdk:11
COPY target/tennis-tournament-0.0.1*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]