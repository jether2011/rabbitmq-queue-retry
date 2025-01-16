# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-amqp)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)

### To test the retry functionality

That project is using an idea for retry message using RabbitMQ exchange TTL. The diagram bellow explain better.
To test the functionality, execute some steps:

1. Inside the project folder, access `docker` folder and execute the command `docker-compose up -d` (to undeploy, `docker-compose down`);
2. Access the RabbitMQ client dashboard `http://localhost:15680/#/` and use the user and password from `.env` file;
3. Execute the cURL bellow to send message and test manually the retry functionality.

```shell
curl --location --request POST 'localhost:8081/v1/api/checkout' \
--header 'Content-Type: application/json' \
--data-raw '{
    "personId": "12345689",
    "paymentMethod": "DEBIT",
    "amount": 21.90
}'
```
Diagram:

<img src="https://raw.githubusercontent.com/jether2011/rabbitmq-queue-retry/master/docker/reliable_rabbit_queues.png" width="800"/>

##
[<img src="https://api.gitsponsors.com/api/badge/img?id=480775738" height="20">](https://api.gitsponsors.com/api/badge/link?p=WhPxLm/G80cBKIJjKwzBEKdFy3xxKIphqtK+cmEcmW7AEhelFILqsTXtqWvsWz9QhdYE6QDHl4TZksiMfdH8AcK5ckpWGpCn3JlZIma/AzNT1ApACWUtDWG1wtRKzPx+iPIlWEOEWAl/lhuShAdjKg==)
