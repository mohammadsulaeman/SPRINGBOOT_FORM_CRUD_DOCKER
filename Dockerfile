FROM openjdk:17
LABEL authors="moham"
EXPOSE 8181
COPY target/springboot-form-crud-mysql-example-0.0.1-SNAPSHOT.jar /springboot-form-crud-mysql-example-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/springboot-form-crud-mysql-example-0.0.1-SNAPSHOT.jar"]