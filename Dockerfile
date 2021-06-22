FROM openjdk:7

WORKDIR /opt/app 

COPY target/*.jar /opt/app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/app.jar" "--httpPort=8080"]
