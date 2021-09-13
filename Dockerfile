FROM openjdk:16
EXPOSE 8000

ADD ./target/cinema-app.jar cinema-app.jar

ENTRYPOINT ["java", "-jar", "cinema-app.jar"]
