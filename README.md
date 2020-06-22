# mssc-beer-service

[![CircleCI](https://circleci.com/gh/NagarajJB/mssc-beer-service.svg?style=svg)](https://circleci.com/gh/NagarajJB/mssc-beer-service)


# Notes
* spring.datasource.initialization-mode=EMBEDDED -> lets to load data.sql 
* localmysql profile -> For complete local setup without service discovery, it wont register in Eureka

* local-discovery profile -> Enables service registering and using feign client implementation to connect to inventory service as configured in LocalDiscoveryConfig and properties(feign.hystrix.enabled=true) file through Eureka.

* spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL -> h2 for development in mysql compatible mode

* Spring Cloud Config looks for bootstrap.properties to find env. It can use profile.

* Zipkin is expected to be running for distribute tracing - Using Docker container is advised

* local-discovery profile -> Enables service registering in Eureka as configured in LocalDiscoveryConfig.java using @EnableDiscoveryClient.

* localmysql profile disables registration with Eureka and spring cloud config client

* if running with localmysql and local-discovery use them as localmysql,local-discovery (latter takes precedence)

* zipkin is disabled in application.properties, spring.zipkin.enabled=false, use 'local' profile(from spring cloud config/repo) to enable zipkin.

# Application
* BrewingService is scheduled to keep checking inventory and trigger brewing request

# Dependencies
* spring-boot-starter-artemis -> JMS support
* spring-cloud-starter-netflix-eureka-client -> Eureka client registration
* spring-cloud-starter-openfeign -> For using OpenFeign client to use load balanced inventory service through eureka
* spring-cloud-starter-config -> For registering to discover config from Spring Cloud Config server
* spring-boot-configuration-processor -> For using custom properties by using @ConfigurationProperties
* spring-cloud-starter-zipkin - Includes Spring Cloud Sleuth(distributed tracing) and Zipkin client