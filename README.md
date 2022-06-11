# network-simulator

## Hi!
Simulator is an application that simulate network environment.

## Project moduls

1. [network-simulator](https://github.com/PiotrStoklosa/network-simulator)


2. [calls-management-system](https://github.com/PiotrStoklosa/calls-management-system)


3. statistics-and-results-module



## Table of contents
* [How to set up this app locally](#How-to-set-up-this-app-locally)
* [How to run an app](#How-to-run-an-app)
* [Instruction for users](#Instruction-for-users)
* [Documantation for programmers](#Documantation-for-programmers)

## How to set up this app locally
### Docker
1. Install Docker. If you are using Windows you can download for instance Docker Desktop application available here: https://docs.docker.com/desktop/windows/install/
2. Change directory to root of project
3. Run ```docker build --build-arg JAR_FILE=build/libs/\*.jar -t network-simulator .```
4. When build is complete, run ```docker run -p 8081:8081 network-simulator ```
5. The application is already running and available at the http://localhost:8081 
## How to run an app

## Instruction for users

## Documantation for programmers


**Technologies:**
- Java 17
- Spring Framework
- Spring Boot
- Gradle
- Log4j2
- JUnit5
- Mockito
