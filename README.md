# Payment Manager

## About project
A service accepts information from payment form, 
stores it into database and sends email notification.
Ideally this application should be split into 2 microservices: 
* payment manager (represented by payment package in current implementation) - 
handles incoming requests with information about transaction, 
saves it into database and sends message
* notification manager (represented by notification package in current version) - 
consumes message and reacts to it (by sending an email)

### Built with
* [OpenJDK 15](http://openjdk.java.net/projects/jdk/15/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
* [Reactive MongoDB](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.reactive)
* [Reactive Kafka](https://projectreactor.io/docs/kafka/release/reference/)
* [Lombok](https://projectlombok.org/)
* [MapStruct](https://mapstruct.org/)

### Getting started
To get started with the application, the following steps are required.

#### Prerequisites
* Docker Engine and docker-compose
* Gmail account with extended permissions (grants to insecure apps)

#### Building application
To build and run application:
 1. clone project `git clone git@github.com:helena128/PaymentManager.git`
 2. navigate to the directory of the project `cd PaymentManager/`.
 3. to send payment notification via email provide credentials to SMTP (`MAIL_USERNAME` and `MAIL_PASSWORD`).
 For simplification, properties of Gmail SMTP server are hardcoded. 
 4. run `MAIL_USERNAME=test@gmail.com MAIL_PASSWORD=password docker-compose up --build`
 
 By default application starts on port `8080`.
 
##### Application properties
To pass properties either set env variables or pass following arguments to `java -jar` command:

| Environment variable | Corresponding property | Description | Example |
| ----| ----| ----| ----|
| MONGO_HOST | --spring.data.mongodb.host | MongoDB host | `localhost` |
| MONGO_PORT | --spring.data.mongodb.port | MongoDB port | `27017` |
| MONGO_USERNAME | --spring.data.mongodb.username | MongoDB username | `admin` |
| MONGO_PASSWORD | --spring.data.mongodb.password | MongoDB password | `password` |
| MONGO_AUTH_DB | --spring.data.mongodb.authentication-database | MongoDB authentication db | `admin` |
| MONGO_DB | --spring.data.mongodb.database | MongoDB database used in app | `payments` |
| MAIL_USERNAME | --notification.email.username | Email address used to send mail | `test@gmail.com` |
| MAIL_PASSWORD | --notification.email.password | Password to email address for sending notifications | `Test123!` |
| KAFKA_SERVERS | --crypto.password | Password used in encryption/decryption of sensitive data | `Password123!` |
| CRYPTO_PASSWD | --crypto.algorithm | Algorithm applied for encryption/decryption of sensitive data | `PBEWithMD5AndTripleDES` |
| CRYPTO_ALGO | --kafka.bootstrapServers | Kafka bootstrap servers | `localhost:9092` |

Default values for these properties (except gmail username and password) are set in docker-compose file.

### API
Service API is represented by [swagger document](https://github.com/helena128/PaymentManager/blob/master/src/main/resources/swagger.yaml),
examples of the requests are given below.

### Examples
##### Example of successful request

Request path: `http://{BASE_URL}/v1/payments`.

Headers: `Content-Type: application/json`

Request path:
```json
{
  "sum": 128.18,
  "cardHolderInfo": {
    "name": "Test User",
    "email": "test@gmail.com"
  },
  "cardInfo": {
    "cardNumber": "5555555555554444",
    "expiryDate": "2022-01-02",
    "cvc": "123"
  },
  "recipient": "Some organization"
}
```

Response: `200`, contains message `Successfully created information`

Expected: a mail with information about transaction sent to the email of cardholder.

#### Example of failed request (invalid card number):
Request path: `http://{BASE_URL}/v1/payments`.

Headers: `Content-Type: application/json`

Request path:
```json
{
  "sum": 128.18,
  "cardHolderInfo": {
    "name": "Test User",
    "email": "test@gmail.com"
  },
  "cardInfo": {
    "cardNumber": "5555555555554445",
    "expiryDate": "2022-01-02",
    "cvc": "123"
  },
  "recipient": "Some organization"
}
```
Response: `400`, response body:
```json
{
  "message": "Invalid card number!"
}
```

#### Contact
In case of any questions feel free to contact me [Elena Cheprasova](mailto:elenatchepr@gmail.com?subject=[GitHub]).
