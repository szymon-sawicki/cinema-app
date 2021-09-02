# cinema app
### Project is still in development. Planned finish - 15 september

Application used for management of cinema network with multiple cinema rooms.  

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

Progress in development: ~60%  
Lines of code: 6900  
Classes: 107  
Unit test: 117  
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

- adding new screening with availability checking

Tickets service:

- generating lists of seats for given screening with their status (boolean isBooked in GetSeatDto)


## NEXT TASKS TO DO

- write some methods in screening service
- creating new ticket with seat reservation
- Rest API
