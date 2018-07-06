FROM openjdk:8u141-jdk-slim
MAINTAINER jeremydeane.net
EXPOSE 8080
RUN mkdir /app/
COPY target/magic-batch-1.0.1.jar /app/
ENTRYPOINT exec java $JAVA_OPTS -jar -Dspring.profiles.active=dk /app/magic-batch-1.0.1.jar