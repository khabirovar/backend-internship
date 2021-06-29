FROM openjdk:8-jre-alpine

ARG JAVA_PARAM="-Xms256M -Xmx1024M -XX:+UseG1GC"
ENV JAVA_PARAM=$JAVA_PARAM

WORKDIR /opt/app 

COPY ./target/*.jar /opt/app/app.jar

RUN chown nobody -R /opt/app

USER 65534

ENV PATH=$PATH:/opt/app

CMD ["sh", "-c", "java ${JAVA_PARAM} -jar /opt/app/app.jar"]
