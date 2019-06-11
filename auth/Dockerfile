FROM java:8
EXPOSE 9092
ADD /target/auth.jar auth.jar
ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","auth.jar"]