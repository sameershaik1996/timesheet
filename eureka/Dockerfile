FROM java:8
EXPOSE 8761
ADD /target/eureka.jar eureka.jar
ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","eureka.jar"]