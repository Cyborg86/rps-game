# Rock Paper Scissors API

This is an implementation of Rock Paper Scissors game REST API for ACME Games company.

It is a Java Spring Boot application REST Service.

## Building
Use Maven to build this application by executing following command in project root:

`mvn clean package`.

This command would assemble an single-JAR Java application in `target` directory.

## Running
###Prerequisites
- The application uses MongoDB as its database.
- The application is bound to 8080 port by default. 

### Starting
Assembled JAR can be started simply by

`java -jar <jar-name>`

where `<jar-name>` is a name of assembled JAR file.  For example:

`java -jar rps-game-0.0.1-SNAPSHOT.jar`

## API Description
User can
 - create a new game,
 - get description of existing game,
 - make moves in existing ongoing game,
 - finalize game.

Each game is identified by random UUID assigned to it when it is created. 

### OpenAPI
API is described using OpenAPI specification:
 - `http://localhost:8080/v3/api-docs` - OpenAPI specification
 - `http://localhost:8080/swagger-ui.html` - Swagger UI that could be used for debugging and testing 

## Design notes
This application uses following frameworks and libraries:
 - Spring Boot 2
 - Spring Data Mongo
 - OpenAPI (Swagger 3)
 - Lombok
 
The application is designed to be stateless, keeping all its state in MongoDB.
This approach allows to achieve horizontal scalability by sharding DB over Game IDs
and introducing additional Application Server as required.

## TODO List

- Add integration tests.
- Performance testing and tuning.
- Consider using other key-value DB instead of Mongo. 
 Mongo was just a first choice since I'm most familiar with it.
