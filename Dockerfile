FROM openjdk:11
VOLUME /tmp
EXPOSE 9043
ADD ./target/service-customer-0.0.1-SNAPSHOT.jar ms-customer.jar
ENTRYPOINT ["java","-jar","/ms-customer.jar"]
