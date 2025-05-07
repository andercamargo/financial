# Financial API
API created to perform financial operations such as wallet transfers for the same user.
As the solution architecture, the Clean Architecture pattern was used. Also, the project was separated into three contexts: entity, infrastructure, and use case.

How tecnologies, frameworks and tools were used:
- Spring Boot
- MongoDB
- Spring Cloud
- Docker
- Docker Compose
- Lombok
- Maven
- Java 21

# Business Rules

The application was created to perform financial operations such as wallet transfers for the same user.
To transfer balance, the wallet needs to have a balance greater than zero and superior to amount to transfer.


# Project structure
In addition, the project was designed and separated into three contexts: entity, infrastructure, and use case.

+ entity
+ infrastructure
+ usecase
 
In the entity namespace: the domain of the application
In the infrastructure namespace: the infrastructure of the application
In the use case namespace: the use case of the application


## Run the application
### Maven

To run the application using Maven, execute the following commands in the root directory of the project:

```bash
#compiling
mvn clean package -U 

#to execute the app, run the following command in the root of the project
java -jar financial/target/financial-0.0.1-SNAPSHOT.jar

```
### Docker Compose

To execute this app with docker compose, execute the following commands in the root of the project:

```bash
docker compose up
```

## Tests

To run the test this app, execute the following commands in the root of the project:
```bash
mvn test 
```
