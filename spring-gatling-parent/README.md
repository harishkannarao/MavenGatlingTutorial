spring-gatling-demo
=========================

Simple showcase of a maven project using spring boot sample rest application and gatling performance test with spring framework for dependency injection.

### Steps to Execute REST Service Performance test

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent**
* Execute **mvn clean install -N**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/spring-boot-sample-rest-service**
* Execute **mvn spring-boot:run**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/test-config**
* Execute **mvn clean install**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test**
* Execute **mvn clean install** or **mvn gatling:execute**
* Gatling html report will be available at **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test/target/gatling/results/**
* Gatling http requests and failure responses will be available at **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test/target/gatling/gatling_output.log**

### Steps to Execute Web App Performance test

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent**
* Execute **mvn clean install -N**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/spring-boot-sample-web-app**
* Execute **mvn spring-boot:run**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/test-config**
* Execute **mvn clean install**
* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-web-app-gatling-performance-test**
* Execute **mvn clean install** or **mvn gatling:execute**
* Gatling html report will be available at **{ROOT_FOLDER}/spring-gatling-parent/sample-web-app-gatling-performance-test/target/gatling/results/**
* Gatling http requests and failure responses will be available at **{ROOT_FOLDER}/spring-gatling-parent/sample-web-app-gatling-performance-test/target/gatling/gatling_output.log**

### Steps to change target environment and profile properties

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test**
* Execute **mvn clean install -DtargetEnvironment=local -Dprofile=load** or **mvn gatling:execute -DtargetEnvironment=local -Dprofile=load**
* Default value of targetEnvironment is **local** and profile is **sanity**

### Steps to single scenario by name

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test**
* Execute **mvn clean install -DscenarioName="Basic CRUD operations"** or **mvn gatling:execute -DscenarioName="Basic CRUD operations"**

### Steps to single scenario by simulation

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test**
* Execute **mvn clean install -Dgatling.simulationClass=com.harishkannarao.simulation.RestCrudSimulation"** or **mvn gatling:execute -Dgatling.simulationClass=com.harishkannarao.simulation.RestCrudSimulation**

### Steps to change Gatling jvm memory through environment variable

* Open a terminal and go to **{ROOT_FOLDER}/spring-gatling-parent/sample-rest-service-gatling-performance-test**
* Set environment variable **MAVEN_OPTS=-Xmx1024m** or higher to increase the jvm memory for running performance test
* Execute **mvn clean install** or **mvn gatling:execute**

