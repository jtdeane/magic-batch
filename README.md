magic-batch
=======================

Built with Java 8+, Spring Boot (2.0.3) Redis (3.0.4)

Tested with JUnit (4.12) and JMeter

Executes via Spring Boot with three Spring Profiles (dev, qa, dk) - assumes `mvn clean install`

* _dev_ Profile processes the batch asynchronously and logs out the data

`mvn spring-boot:run -Drun.arguments="-Xmx256m,-Xms128m"`

* _qa_ Profile requires Redis running (configured via application-qa.properties)

`mvn spring-boot:run -Drun.profiles=qa -Drun.arguments="-Xmx256m,-Xms128m"`

* _dk_ Profile requires Docker (configured via application-dk.properties)

`docker-compose up`

or update `docker-compose.yml` and instead of building pull down image

`image: "jtdeane/magic-batch"`

then execute compose

`docker-compose up`


## Submit Batch (dev, qa)

POST `http://localhost:8080/batch`

Content-Type `application/json`

Batch JSON (_example_)

    {"batchId":"A1234","date":"09252017","orders":[
    {"id":"X1351","customer":"Turner","item":"Rings","amount":4},
    {"id":"X1352","customer":"Turner","item":"Dice","amount":2},
    {"id":"X1353","customer":"Turner","item":"Scarf","amount":1}
    ]}
    
See `src/main/resources/application.properties` for available items.

## Query Order (qa)

Assuming a batch of orders have been posted and stored in Redis, then individual order totals can be retrieved.

GET `http://localhost:8080/order/{id}`
