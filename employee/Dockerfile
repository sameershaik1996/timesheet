FROM java:8
EXPOSE 9090
ADD /target/employee.jar employee.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","employee.jar"]