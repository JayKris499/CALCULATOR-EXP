FROM amazoncorretto:17
WORKDIR /app

COPY target/*.jar /app/calculator_application.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/calculator_application.jar"]