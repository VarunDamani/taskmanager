# TaskManager

A Java and SpringBoot based backend application that exposes API to manage lifecycle Task Groups and it's associated
tasks.

## Tech Used

* [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) [version 8.4.13]
* [Java](https://www.oracle.com/java/) [version 21]
* [SpringBoot](https://spring.io/projects/spring-boot/) [version 3.5.3]
* [MySQL](https://dev.mysql.com/doc/refman/8.4/en/introduction.html) [version 8.1]
* [MapStruct](https://mapstruct.org/) [version 1.6.3]
* [Flyway](https://documentation.red-gate.com/fd/flyway-documentation-138346877.html)
* [H2 In-memory Database](https://www.h2database.com/html/main.html) - for integration test
* [jaCoCo]() - for code coverage reports
* [Docker](https://www.docker.com/)

# Getting Started

[See detailed help guide →](HELP.md)

# Status

## Things implemented so far

1. Exposed APIs to manage task list lifecycle
2. Swagger documentation in place
3. Flyway Migration to manage DB changelogs
4. Added integration tests
5. Jacoco integration to monitor test coverage(currently >90%)
6. Implemented Basic auth and method level Role based authorization checks
7. Added basic audit fields in database tables
8. Dockerfile for cloud native deployments
9. docker-compose for spinning up middleware locally for consistent development experience

## Things that could added ot improved

1. Automate the entire local running setup for smoother developer onboarding and consistent experience
2. Move away from Basic auth to JWT based auth
3. Redis for caching and also distributed locking to manage concurrent request
4. Support of load testing(K6)
5. Database connection pool tuning
6. Improved auditing database records

