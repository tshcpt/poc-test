FROM openjdk:8
EXPOSE 8080
ADD target/poc-1.0.0-SNAPSHOT.jar poc-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/poc-1.0.0-SNAPSHOT.jar"]