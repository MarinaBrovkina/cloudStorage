FROM openjdk:8-jdk-alpine

EXPOSE 8082

ADD target/cloudStorage-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
