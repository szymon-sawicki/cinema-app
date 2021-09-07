# cinema app
### Project is still in development. Planned finish - 15 september

Backend for management system for network of cinemas. 

Technology stack:  

- Java 16
- Spring framework
- Jdbi
- MySQL
- Spark web framework
- Lombok
- Google Guava

Build tool: Maven

Testing:

- Junit 5
- AssertJ
- Mockito

## Building and running of project

What you need: Java 16, Maven, Docker  
  
After downloading of source files, project must be compiled to jar file with command "mvn clean install".
Whole environment (app + MySQL database) can be build with docker compose with command:  

- docker compose up -d --build

By starting of containers main class App is launched. At the moment main class loaded sample data to database that is used to test new features.  
In the future App class will be starting rest api controllers.


## Some statistics

Progress in development: ~80%  
Lines of code: 7700
Classes: 127
Unit test: 121  
Test-coverage: ~65% 

## Implemented domain objects

- Address  
- Cinema  
- Cinema Room  
- Movie  
- Screening  
- Seat  
- Ticket  
- User  


## Working features at the moment

Cinemas service:

- creating new cinema with multiple rooms
- adding new rooms to existing cinema
- searching cinema by name
- searching all cinemas from city

Movies service:

- adding new movie
- searching movie by name
- finding all movies from given genre

Screenings service:  

Finding of screening operates on view that contains most important info about screening fetched from tables using join statement.   

- adding new screening with availability checking
- finding screening by keyword
- finding screening by movie
- finding screening by date  
- finding screening by cinema  
- deleting screening  
- updating screening  

Tickets service:  

- creating tickets with availability checking for given screening
- generating map with booked seat of given screening
- deleting ticket  

Users service:  

- creating new user  
- updating user  
- deleting user  

## NEXT TASKS TO DO  

- implement all missing methods in services and test them  
- write all javadoc stubs  
- REST Api  
- postman documentation
