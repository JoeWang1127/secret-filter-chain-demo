### Running with Spring Boot 2.7.8
In the `main` branch

* Start up the application
* Run `curl localhost:8080`, the terminal will return `No secrets here!`
* Run `curl localhost:8080/topsecret`, the terminal will return `{"timestamp":"2023-02-12T21:26:39.147+00:00","status":403,"error":"Forbidden","path":"/topsecret"}`
