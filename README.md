### Running with Spring Boot 2.7.8
In the `main` branch

* Start up the application
* Run `curl localhost:8080`, the terminal will return `No secrets here!`

### Running with Spring Boot 3.0.2
In the `spring-boot3` branch

* Start up the application
* Run `curl localhost:8080`, nothing returns to the terminal. It seems that
the `GetMapping` is not called.
