FROM openjdk:7

WORKDIR /opt/app 

COPY target/*.jar /opt/app/

EXPOSE 8080

CMD ["java", "-jar", "*.jar" "--httpPort=8080"]
