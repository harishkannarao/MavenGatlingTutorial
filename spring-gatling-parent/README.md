spring-gatling-demo
=========================

Simple showcase of a maven project using spring boot sample rest application and gatling performance test with spring framework for dependency injection.

### Steps to Execute

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/spring-boot-sample-rest-service**
* Execute **mvn spring-boot:run**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/test-config**
* Execute **mvn clean install**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test**
* Execute **mvn clean install** or **mvn gatling:execute**
* Gatling html report will be available at **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test/target/gatling/results/**
* Gatling http requests and failure responses will be available at **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test/target/gatling/gatling_request_response.log**

### Steps to override property values through environment variables (Spring framework)

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test**
* Set environment variable CONSTANT_USERS_PER_SECOND=1
* Set environment variable DURATION_IN_MINUTES=1
* Execute **mvn clean install** or **mvn gatling:execute**
