FROM openjdk:8-alpine

WORKDIR /opt/app 

RUN mv -r ./target/*.jar ./target/app.jar

COPY ./target/*.jar /opt/app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/app.jar"]
