# Urbi API

The microservice is developed in java 11, with Spring Boot 2.3.x framework.
It exposes APIs necessary for the test.

The context-path is the default and it is expose on port ```8080 ```

The actuator is exposed to the following path:
  - ```/actuator```
 
All APIs are documented with Swagger


### Installation

The microservice:
  - requires Java v11+ and Maven  v3+ to run.
  - generates the "urbi" docker image through the Dockerfile contained in the project
  - uses docker image for local run container or (optional) push image on a docker registry

### Local deploy (debug)

You must create new configuration Application -> Edit Configurations... with Template -> Spring Boot 
and define as Main class "it.jump3.urbi.UrbiDemoApplication".

That's it! You can run the Edit Configuration in debug!