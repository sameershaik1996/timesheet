FROM java:8
EXPOSE 9094
ADD /target/zuul.jar zuul.jar
ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","zuul.jar"]