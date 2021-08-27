FROM openjdk:16
EXPOSE 8080

ADD ./target/cinema-app.jar cinema-app.jar

ENTRYPOINT ["java", "-jar", "cinema-app.jar"]
