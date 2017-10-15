magic-batch
=======================

Built with Java 8+, Spring Boot (1.5.6) Redis (3.0.4)

Tested with JUnit (4.12) and JMeter

Executes via Spring Boot with two Spring Profiles (dev, qa)

* _dev_ Profile processes the batch asynchronously out logs out the data

* _qa_ Profile requires Redis running (configured via application-qa.properties

## Submit Batch (dev, qa)

POST `http://localhost:8080/batch/{id}`

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
