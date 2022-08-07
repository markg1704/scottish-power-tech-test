# scottish-power-tech-test

SpringBoot application to fulfil the Scottish Power Mid-Level Tech Test.

Application represents a simple RESTful service to retrieve SMART meter reading details linked to an account.

Single get request is set up with the format;

GET http://localhost:8081/api/smart/reads?accountnumber=xxxxxxx

Project is in Java version 8.

Project uses a H2 in memory database.  Some 'dummy' test data is pre-loaded via the ScottishpowertestApplication.class initDb() method.
This method, annotated with @PostConstruct can be commented out if not required.

The dummy data account number is '1234567890'.  The application can therefore quickly be dev-tested (e.g. Postman) using;

http://localhost:8081/api/smart/reads?accountnumber=1234567890

