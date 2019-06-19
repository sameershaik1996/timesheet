FROM test:latest

COPY /target/timesheet.jar timesheet.jar
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=test", "timesheet.jar"]
