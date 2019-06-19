FROM java:8
FROM maven:3.5-jdk-8-alpine
EXPOSE 9090
ADD /target/employee.jar employee.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","employee.jar"]