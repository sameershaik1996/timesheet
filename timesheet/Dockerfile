FROM java:8
EXPOSE 8084
ADD /target/timesheet.jar timesheet.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","timesheet.jar"]