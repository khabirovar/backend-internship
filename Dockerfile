FROM openjdk:7 #tomcat:9.0-jdk11-adoptopenjdk-hotspot

WORKDIR /opt/app 

COPY target/*.jar /opt/app/

EXPOSE 8080

CMD ["java", "-jar", "*.jar" "--httpPort=8080"]
