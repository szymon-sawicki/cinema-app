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

## Some statistics

Progress in development: ~50%  
Lines of code: 4300  
Classes: 72  
Unit test: 74  
Test-coverage: ~65% 

## Implemented domain objects

- Address  
- Cinema  
- Cinema Room  
- Seat  
- Screening  
- Movie  

I'm planning to add some in next time


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


## NEXT TASKS TO DO

- refactor test of adding cinema
- find good way to avoid setters in domain objects





