FROM java:8
EXPOSE 9090
CMD ["mvn clean install"]
ADD /target/employee.jar employee.jar
ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","employee.jar"]