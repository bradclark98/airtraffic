Binary Fountain Air Traffic Control Program

The Air Traffic Control assignment was implemented as a Spring Boot application with REST endpoints.  

To execute the project, 

1. Clone this repo and build
  * git clone https://github.com/bradclark98/airtraffic.git
  * cd airtraffic
  * mvn clean install
2. Start Service
  * mvn spring-boot:run

At this point the application should be started on 8080, I added Swagger to the end points to easily test the REST services.  

1. To test with Swagger navigate to 
  * http://localhost:8080/swagger-ui.html
2. Under each endpoint there is a try it tag that allows the user to submit GETS and POSTS.

