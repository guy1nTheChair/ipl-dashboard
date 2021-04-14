FROM adoptopenjdk/openjdk16:alpine

COPY target/dashboard-1.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

