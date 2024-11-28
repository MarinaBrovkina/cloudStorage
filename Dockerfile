FROM openjdk:22.0.1 as build

EXPOSE 9292

ADD target/cloudStorage-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
